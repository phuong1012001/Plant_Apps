package com.phngsapplication.app.modules.detailarticles.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailArticlesBinding
import com.phngsapplication.app.modules.detailarticles.`data`.viewmodel.DetailArticlesVM
import kotlin.String
import kotlin.Unit

class DetailArticlesActivity :
    BaseActivity<ActivityDetailArticlesBinding>(R.layout.activity_detail_articles) {
  private val viewModel: DetailArticlesVM by viewModels<DetailArticlesVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.detailArticlesVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "DETAIL_ARTICLES_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DetailArticlesActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
