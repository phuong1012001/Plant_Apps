package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityIntroduction2Binding
import kotlin.String
import kotlin.Unit

class Introduction2Activity :
    BaseActivity<ActivityIntroduction2Binding>(R.layout.activity_introduction_2)
{

  override fun onInitialized(): Unit {
  }

  override fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = Introduction3Activity.getIntent(this, null)
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
