package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityIntroduction1Binding
import kotlin.String
import kotlin.Unit

class Introduction1Activity :
    BaseActivity<ActivityIntroduction1Binding>(R.layout.activity_introduction_1)
{

  override fun onInitialized(): Unit {
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
