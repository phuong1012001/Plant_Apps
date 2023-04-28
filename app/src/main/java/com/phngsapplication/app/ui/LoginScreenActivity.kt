package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityLoginScreenBinding
import kotlin.String
import kotlin.Unit

class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding>(R.layout.activity_login_screen)
{

  private lateinit var firebaseAuth: FirebaseAuth

  private lateinit var progressDialog: ProgressDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAuth = FirebaseAuth.getInstance()

    //init progress Dialog
    progressDialog = ProgressDialog(this)
    progressDialog.setTitle("Please wait !")
    progressDialog.setCanceledOnTouchOutside(false)
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
      val destIntent = MainActivity.getIntent(this, null)

      if(email.isNotEmpty() && pass.isNotEmpty()){
        progressDialog.setMessage("Logging...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
          if(it.isSuccessful){
            startActivity(destIntent)
            val verify = firebaseAuth.currentUser?.isEmailVerified
            if(verify == true){
              val user = firebaseAuth.currentUser
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
