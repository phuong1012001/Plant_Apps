package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailPlantBinding
import kotlin.String
import kotlin.Unit

class DetailPlantActivity : BaseActivity<ActivityDetailPlantBinding>(R.layout.activity_detail_plant)
{

  override fun onInitialized(): Unit {

  }

  override fun setUpClicks(): Unit {
//    binding.imageArrowleft.setOnClickListener {
//      finish()
//    }
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
