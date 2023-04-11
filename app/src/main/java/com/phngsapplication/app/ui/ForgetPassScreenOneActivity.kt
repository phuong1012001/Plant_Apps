package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityForgetPassScreenOneBinding
import kotlin.String
import kotlin.Unit

class ForgetPassScreenOneActivity :
    BaseActivity<ActivityForgetPassScreenOneBinding>(R.layout.activity_forget_pass_screen_one) {
  private lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAuth = FirebaseAuth.getInstance()
  }
  override fun onInitialized(): Unit {
  }

  override fun setUpClicks(): Unit {
    binding.btnSendEmail.setOnClickListener {
      val email = binding.etEmailOne.text.toString()
      if(email.isNotEmpty()){
        firebaseAuth.sendPasswordResetEmail(email)
          .addOnSuccessListener {
            Toast.makeText(this, "Please check your Email !!!", Toast.LENGTH_SHORT).show()
          }
          .addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
          }
      }else{
        Toast.makeText(this, "Empty Fields Are not Valid !!", Toast.LENGTH_SHORT).show()
      }

      val destIntent = ForgetPassScreenZeroActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "FORGET_PASS_SCREEN_ONE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ForgetPassScreenOneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
