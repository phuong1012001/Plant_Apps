package com.phngsapplication.app.modules.addingnewplant.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityAddingNewPlantBinding
import com.phngsapplication.app.modules.addingnewplant.`data`.viewmodel.AddingNewPlantVM
import kotlin.String
import kotlin.Unit

class AddingNewPlantActivity :
    BaseActivity<ActivityAddingNewPlantBinding>(R.layout.activity_adding_new_plant) {
  private val viewModel: AddingNewPlantVM by viewModels<AddingNewPlantVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.addingNewPlantVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "ADDING_NEW_PLANT_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, AddingNewPlantActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
