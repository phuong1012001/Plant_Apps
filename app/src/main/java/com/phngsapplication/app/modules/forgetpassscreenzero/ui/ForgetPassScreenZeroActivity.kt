package com.phngsapplication.app.modules.forgetpassscreenzero.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityForgetPassScreenZeroBinding
import com.phngsapplication.app.modules.forgetpassscreenone.ui.ForgetPassScreenOneActivity
import com.phngsapplication.app.modules.forgetpassscreenzero.`data`.viewmodel.ForgetPassScreenZeroVM
import com.phngsapplication.app.modules.loginscreen.ui.LoginScreenActivity
import com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.ui.Onboarding2LearnManyPlantsSpeciesActivity
import kotlin.String
import kotlin.Unit

class ForgetPassScreenZeroActivity :
    BaseActivity<ActivityForgetPassScreenZeroBinding>(R.layout.activity_forget_pass_screen_zero) {
  private val viewModel: ForgetPassScreenZeroVM by viewModels<ForgetPassScreenZeroVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.forgetPassScreenZeroVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = LoginScreenActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "FORGET_PASS_SCREEN_ZERO_ACTIVITY"

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ForgetPassScreenZeroActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
