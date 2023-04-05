package com.phngsapplication.app.modules.introduction1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityIntroduction1Binding
import com.phngsapplication.app.modules.introduction1.`data`.viewmodel.Introduction1VM
import com.phngsapplication.app.modules.introduction2.ui.Introduction2Activity
import kotlin.String
import kotlin.Unit

class Introduction1Activity :
    BaseActivity<ActivityIntroduction1Binding>(R.layout.activity_introduction_1)
    {
  private val viewModel: Introduction1VM by viewModels<Introduction1VM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.introduction1VM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = Introduction2Activity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "INTRODUCTION1_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Introduction1Activity::class.java)
      destIntent.putExtra("Introduction1", bundle)
      return destIntent
    }
  }
}
