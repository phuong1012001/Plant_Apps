package com.phngsapplication.app.modules.species.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivitySpeciesBinding
import com.phngsapplication.app.modules.species.`data`.viewmodel.SpeciesVM
import kotlin.String
import kotlin.Unit

class SpeciesActivity : BaseActivity<ActivitySpeciesBinding>(R.layout.activity_species) {
  private val viewModel: SpeciesVM by viewModels<SpeciesVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.speciesVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "SPECIES_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SpeciesActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
