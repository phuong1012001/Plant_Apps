package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.ActivityForgetPassScreenZeroBinding
import kotlin.String
import kotlin.Unit

class ForgetPassScreenZeroActivity : AppCompatActivity(){

  private lateinit var binding: ActivityForgetPassScreenZeroBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_pass_screen_zero)

    setUpClicks()
  }

  fun setUpClicks(): Unit {
    binding.btnNext.setOnClickListener {
      val destIntent = LoginScreenActivity.getIntent(this, null)
      startActivity(destIntent)
      this.finish()
    }
  }

  companion object {
    const val TAG: String = "FORGET_PASS_SCREEN_ZERO_ACTIVITY"

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ForgetPassScreenZeroActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
