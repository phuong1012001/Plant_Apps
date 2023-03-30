package com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityOnboarding3ReadManyArticlesAboutPlantsBinding
import com.phngsapplication.app.modules.loginscreen.ui.LoginScreenActivity
import com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.`data`.viewmodel.Onboarding3ReadManyArticlesAboutPlantsVM
import kotlin.String
import kotlin.Unit

class Onboarding3ReadManyArticlesAboutPlantsActivity :
    BaseActivity<ActivityOnboarding3ReadManyArticlesAboutPlantsBinding>(R.layout.activity_onboarding_3_read_many_articles_about_plants)
    {
  private val viewModel: Onboarding3ReadManyArticlesAboutPlantsVM by
      viewModels<Onboarding3ReadManyArticlesAboutPlantsVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.onboarding3ReadManyArticlesAboutPlantsVM = viewModel
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
      val destIntent = Intent(context, Onboarding3ReadManyArticlesAboutPlantsActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
