package com.phngsapplication.app.modules.detailplant.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailPlantBinding
import com.phngsapplication.app.modules.detailplant.`data`.viewmodel.DetailPlantVM
import kotlin.String
import kotlin.Unit

class DetailPlantActivity : BaseActivity<ActivityDetailPlantBinding>(R.layout.activity_detail_plant)
    {
  private val viewModel: DetailPlantVM by viewModels<DetailPlantVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.detailPlantVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "DETAIL_PLANT_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DetailPlantActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
