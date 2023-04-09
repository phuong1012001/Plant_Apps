package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityLoginScreenBinding
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import kotlin.String
import kotlin.Unit

class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding>(R.layout.activity_login_screen)
{

  private lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAuth = FirebaseAuth.getInstance()
  }

  override fun onInitialized(): Unit {
  }

  override fun setUpClicks(): Unit {
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
      val destIntent = HomepageActivity.getIntent(this, null)

      if(email.isNotEmpty() && pass.isNotEmpty()){
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
          if(it.isSuccessful){
            startActivity(destIntent)
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
