package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.ActivitySignupScreenBinding
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.String
import kotlin.Unit

class SignupScreenActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySignupScreenBinding

  private lateinit var firebaseAuth: FirebaseAuth

  private lateinit var progressDialog: ProgressDialog

  private var db = Firebase.firestore

  private var email = ""
  private var pass = ""
  private var name = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_screen)
    firebaseAuth = FirebaseAuth.getInstance()

    //init progress Dialog
    progressDialog = ProgressDialog(this)
    progressDialog.setTitle("Please wait !")
    progressDialog.setCanceledOnTouchOutside(false)

    setUpClicks()
  }

  fun setUpClicks(): Unit {
    binding.btnSignUpOne.setOnClickListener {
      email = binding.email.text.toString()
      pass = binding.password.text.toString()
      name = binding.fullName.text.toString()

      val destIntent = LoginScreenActivity.getIntent(this, null)
      if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()){
        if(emailValidator(email) && isValidPassword(pass)) {
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if(it.isSuccessful){
              firebaseAuth.currentUser?.sendEmailVerification()
                ?.addOnSuccessListener {
                  //account created, --> add user infor in db

                  Toast.makeText(this, "Please verify your Email !!!", Toast.LENGTH_SHORT).show()
                }
                ?.addOnFailureListener {e->
                  progressDialog.dismiss()
                  Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
              updateUserInfo()
              updateUserInfoFireStore()
              startActivity(destIntent)
              this.finish()
            }else{
              Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
          }
        }
      }else{
        Toast.makeText(this, "Empty Fields Are not Valid !!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun updateUserInfoFireStore() {
    val timestamp = System.currentTimeMillis()
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val userMap = hashMapOf(
      "id" to userId,
      "email" to email,
      "name" to name,
      "profileImage" to "",
      "timestamp" to timestamp,
    )



    db.collection("User").document(userId).set(userMap)
      .addOnSuccessListener {
        //user info save, open user dashboard
        //progressDialog.dismiss()
        Toast.makeText(this, "Add FireStore Successfully...", Toast.LENGTH_SHORT).show()
      }
      .addOnFailureListener{e->
        //progressDialog.dismiss()
        Toast.makeText(this, "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
      }
  }

  private fun updateUserInfo() {
    // Save user info - Firebase realtime DB
    progressDialog.setMessage("Saving user infor...")

    //timestamp
    val timestamp = System.currentTimeMillis()

    //get current user id, since user is registered so we can get it now
    val uid = firebaseAuth.uid

    //setup data to add db
    val hashMap: HashMap<String, Any?> = HashMap()
    hashMap["uid"] = uid
    hashMap["email"] = email
    hashMap["name"] = name
    hashMap["profileImage"] = ""
    hashMap["timestamp"] = timestamp

    //set data to db
    val ref = FirebaseDatabase.getInstance().getReference("Users")
    ref.child(uid!!)
      .setValue(hashMap)
      .addOnSuccessListener {
        //user info save, open user dashboard
        progressDialog.dismiss()
        Toast.makeText(this, "Account created...", Toast.LENGTH_SHORT).show()
      }
      .addOnFailureListener{e->
        progressDialog.dismiss()
        Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
      }

  }

  private fun emailValidator(email: String) :  Boolean{
    // Android offers the inbuilt patterns which the entered
    // data from the EditText field needs to be compared with
    // In this case the entered data needs to compared with
    // the EMAIL_ADDRESS, which is implemented same below
    if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      //Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show()
      return true
    } else {
      Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show()
      return false
    }
  }

  private fun isValidPassword(password: String?): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{11,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(password)
    if(matcher.matches()){
      return true
    }else
      Toast.makeText(this, "Password consists of 11 characters with letters, numbers and special characters, lowercase and uppercase !!", Toast.LENGTH_SHORT).show()
    return false
  }


  companion object {
    const val TAG: String = "SIGNUP_SCREEN_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SignupScreenActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
