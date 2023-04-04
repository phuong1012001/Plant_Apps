package com.phngsapplication.app.modules.loginscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityLoginScreenBinding
import com.phngsapplication.app.modules.forgetpassscreenone.ui.ForgetPassScreenOneActivity
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import com.phngsapplication.app.modules.loginscreen.`data`.viewmodel.LoginScreenVM
import com.phngsapplication.app.modules.signupscreen.ui.SignupScreenActivity
import org.koin.android.ext.android.bind
import kotlin.String
import kotlin.Unit

class LoginScreenActivity : AppCompatActivity()
    {
      private lateinit var binding: ActivityLoginScreenBinding
  private val viewModel: LoginScreenVM by viewModels<LoginScreenVM>()
  //private lateinit var binding: ActivityLoginScreenBinding
  private lateinit var firebaseAuth: FirebaseAuth

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtSignUp.setOnClickListener{
          val intent = Intent(this, SignupScreenActivity::class.java)
          startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
          val email = binding.etEmailOne.text.toString()
          val pass = binding.etGroupEightyFive.text.toString()

          if(email.isNotEmpty() && pass.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
              if(it.isSuccessful){
                val intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
              }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
              }
            }
          }else{
            Toast.makeText(this, "Empty Fields Are not Valid !!", Toast.LENGTH_SHORT).show()
          }


        }

      }
//      override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//          val intent = Intent(this, HomepageActivity::class.java)
//          startActivity(intent)
//        }
//      }
fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.loginScreenVM = viewModel
  }

  fun setUpClicks(): Unit {
    binding.txtSignUp.setOnClickListener {
      val destIntent = SignupScreenActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.txtForgotPassword.setOnClickListener {
      val destIntent = ForgetPassScreenOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnLogin.setOnClickListener{
      val destIntent = HomepageActivity.getIntent(this, null)
      startActivity(destIntent)
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
