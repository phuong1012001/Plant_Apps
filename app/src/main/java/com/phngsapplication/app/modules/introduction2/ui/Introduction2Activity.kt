package com.phngsapplication.app.modules.introduction2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityIntroduction2Binding
import com.phngsapplication.app.modules.introduction2.`data`.viewmodel.Introduction2VM
import com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.ui.Onboarding3ReadManyArticlesAboutPlantsActivity
import kotlin.String
import kotlin.Unit

class Introduction2Activity :
    BaseActivity<ActivityIntroduction2Binding>(R.layout.activity_introduction_2)
    {
  private val viewModel: Introduction2VM by
      viewModels<Introduction2VM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.introduction2VM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = Onboarding3ReadManyArticlesAboutPlantsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "INTRODUCTION_2_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Introduction2Activity::class.java)
      destIntent.putExtra("introduction2", bundle)
      return destIntent
    }
  }
}
