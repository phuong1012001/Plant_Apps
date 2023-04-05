package com.phngsapplication.app.modules.introduction3.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityIntroduction3Binding
import com.phngsapplication.app.modules.loginscreen.ui.LoginScreenActivity
import com.phngsapplication.app.modules.introduction3.`data`.viewmodel.Introduction3VM
import kotlin.String
import kotlin.Unit

class Introduction3Activity :
    BaseActivity<ActivityIntroduction3Binding>(R.layout.activity_introduction_3)
    {
  private val viewModel: Introduction3VM by
      viewModels<Introduction3VM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.introduction3VM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnSignIn.setOnClickListener {
      val destIntent = LoginScreenActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "ONBOARDING3READ_MANY_ARTICLES_ABOUT_PLANTS_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Introduction3Activity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
