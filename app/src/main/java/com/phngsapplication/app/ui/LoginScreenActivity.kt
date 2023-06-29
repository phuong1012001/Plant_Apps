package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.ActivityLoginScreenBinding
import kotlin.String
import kotlin.Unit

class LoginScreenActivity: AppCompatActivity()
{
  private lateinit var binding: ActivityLoginScreenBinding

  private lateinit var firebaseAuth: FirebaseAuth

  private lateinit var progressDialog: ProgressDialog

  private var backPressedTime = 0L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)
    firebaseAuth = FirebaseAuth.getInstance()

    //init progress Dialog
    progressDialog = ProgressDialog(this)
    progressDialog.setTitle("Please wait !")
    progressDialog.setCanceledOnTouchOutside(false)
    setUpClicks()
  }

  override fun onBackPressed() {
    if (backPressedTime + 2000 > System.currentTimeMillis()){
      super.onBackPressed()
      return;
    } else {
      Toast.makeText(this, "Press again to exit!!!", Toast.LENGTH_SHORT).show()
    }
    backPressedTime = System.currentTimeMillis()
  }

  fun setUpClicks(): Unit {
    binding.btnSignUp.setOnClickListener {
      val destIntent = SignupScreenActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnForgotPassword.setOnClickListener {
      val destIntent = ForgetPassScreenOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnLogin.setOnClickListener{
      val email = binding.email.text.toString()
      val pass = binding.password.text.toString()
      val destIntent = MainActivity.getIntent(this, null)

      if(email.isNotEmpty() && pass.isNotEmpty()){
        progressDialog.setMessage("Logging...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
          if(it.isSuccessful){
            val verify = firebaseAuth.currentUser?.isEmailVerified
            if(verify == true){
              startActivity(destIntent)
            }else{
              Toast.makeText(this, "Please verify your Email !!!", Toast.LENGTH_SHORT).show()
            }
          }else{
            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
          }
        }
      }else{
        Toast.makeText(this, "Empty Fields Are not Valid !!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    const val TAG: String = "LOGIN_SCREEN_ACTIVITY"

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, LoginScreenActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
