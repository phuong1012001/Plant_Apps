package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivitySignupScreenBinding
import kotlin.String
import kotlin.Unit

class SignupScreenActivity :
  BaseActivity<ActivitySignupScreenBinding>(R.layout.activity_signup_screen) {

  private lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAuth = FirebaseAuth.getInstance()
  }


  override fun onInitialized(): Unit {
  }

  override fun setUpClicks(): Unit {
    binding.btnSignUpOne.setOnClickListener {
      val email = binding.email.text.toString()
      val pass = binding.password.text.toString()
      val name = binding.fullName.text.toString()

      val destIntent = LoginScreenActivity.getIntent(this, null)
      if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
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
    const val TAG: String = "SIGNUP_SCREEN_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SignupScreenActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
