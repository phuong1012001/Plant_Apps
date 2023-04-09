package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityForgetPassScreenOneBinding
import kotlin.String
import kotlin.Unit

class ForgetPassScreenOneActivity :
    BaseActivity<ActivityForgetPassScreenOneBinding>(R.layout.activity_forget_pass_screen_one) {

  override fun onInitialized(): Unit {
  }

  override fun setUpClicks(): Unit {
    binding.btnSendEmail.setOnClickListener {
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
