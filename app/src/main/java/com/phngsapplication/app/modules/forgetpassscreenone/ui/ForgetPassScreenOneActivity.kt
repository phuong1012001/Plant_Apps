package com.phngsapplication.app.modules.forgetpassscreenone.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityForgetPassScreenOneBinding
import com.phngsapplication.app.modules.forgetpassscreenone.`data`.viewmodel.ForgetPassScreenOneVM
import kotlin.String
import kotlin.Unit

class ForgetPassScreenOneActivity :
    BaseActivity<ActivityForgetPassScreenOneBinding>(R.layout.activity_forget_pass_screen_one) {
  private val viewModel: ForgetPassScreenOneVM by viewModels<ForgetPassScreenOneVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.forgetPassScreenOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
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