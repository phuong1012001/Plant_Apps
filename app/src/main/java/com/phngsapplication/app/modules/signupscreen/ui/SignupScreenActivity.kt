package com.phngsapplication.app.modules.signupscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.signupScreenVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnSignUpOne.setOnClickListener {
      val destIntent = LoginScreenActivity.getIntent(this, null)
      startActivity(destIntent)
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
