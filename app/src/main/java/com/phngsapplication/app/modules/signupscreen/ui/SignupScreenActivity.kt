package com.phngsapplication.app.modules.signupscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivitySignupScreenBinding
import com.phngsapplication.app.modules.forgetpassscreenzero.ui.ForgetPassScreenZeroActivity
import com.phngsapplication.app.modules.loginscreen.ui.LoginScreenActivity
import com.phngsapplication.app.modules.signupscreen.`data`.viewmodel.SignupScreenVM
import kotlin.String
import kotlin.Unit

class SignupScreenActivity :
  BaseActivity<ActivitySignupScreenBinding>(R.layout.activity_signup_screen) {
  private val viewModel: SignupScreenVM by viewModels<SignupScreenVM>()

  private lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAuth = FirebaseAuth.getInstance()
  }


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.signupScreenVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnSignUpOne.setOnClickListener {
      val email = binding.etEmailOne.text.toString()
      val pass = binding.etGroupNinety.text.toString()
      val name = binding.etGroupNinetyTwo.text.toString()

      val destIntent = LoginScreenActivity.getIntent(this, null)
      if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
          if (it.isSuccessful) {
            firebaseAuth.currentUser?.sendEmailVerification()
              ?.addOnSuccessListener {
                Toast.makeText(this, "Please verify your Email !!!", Toast.LENGTH_SHORT).show()
              }
              ?.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
              }
            startActivity(destIntent)
          } else {
            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
          }
        }
      }
          else{
          Toast.makeText(this, "Empty Fields Are not Valid !!", Toast.LENGTH_SHORT).show()
        }
    }
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
