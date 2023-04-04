package com.phngsapplication.app.modules.onboarding1identifyplants.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityOnboarding1IdentifyPlantsBinding
import com.phngsapplication.app.modules.onboarding1identifyplants.`data`.viewmodel.Onboarding1IdentifyPlantsVM
import com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.ui.Onboarding2LearnManyPlantsSpeciesActivity
import kotlin.String
import kotlin.Unit

//class Onboarding1IdentifyPlantsActivity : AppCompatActivity(){
//  private lateinit var binding: ActivityOnboarding1IdentifyPlantsBinding
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding_1_identify_plants)
//
//    binding.btnNext.setOnClickListener(){
//      startActivity(Intent(this@Onboarding1IdentifyPlantsActivity, Onboarding2LearnManyPlantsSpeciesActivity::class.java))
//    }
//  }
//
//
//}

class Onboarding1IdentifyPlantsActivity :
    BaseActivity<ActivityOnboarding1IdentifyPlantsBinding>(R.layout.activity_onboarding_1_identify_plants)
    {
  private val viewModel: Onboarding1IdentifyPlantsVM by viewModels<Onboarding1IdentifyPlantsVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.onboarding1IdentifyPlantsVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = Onboarding2LearnManyPlantsSpeciesActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "ONBOARDING1IDENTIFY_PLANTS_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Onboarding1IdentifyPlantsActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
