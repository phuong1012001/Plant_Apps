package com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityOnboarding2LearnManyPlantsSpeciesBinding
import com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.`data`.viewmodel.Onboarding2LearnManyPlantsSpeciesVM
import com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.ui.Onboarding3ReadManyArticlesAboutPlantsActivity
import kotlin.String
import kotlin.Unit

class Onboarding2LearnManyPlantsSpeciesActivity :
    BaseActivity<ActivityOnboarding2LearnManyPlantsSpeciesBinding>(R.layout.activity_onboarding_2_learn_many_plants_species)
    {
  private val viewModel: Onboarding2LearnManyPlantsSpeciesVM by
      viewModels<Onboarding2LearnManyPlantsSpeciesVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.onboarding2LearnManyPlantsSpeciesVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = Onboarding3ReadManyArticlesAboutPlantsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "ONBOARDING2LEARN_MANY_PLANTS_SPECIES_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Onboarding2LearnManyPlantsSpeciesActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
