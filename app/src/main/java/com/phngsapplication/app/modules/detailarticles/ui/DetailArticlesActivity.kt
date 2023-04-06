package com.phngsapplication.app.modules.detailarticles.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailArticlesBinding
import com.phngsapplication.app.modules.articles.data.model.ArticlesRowModel
import com.phngsapplication.app.modules.articles.ui.ArticlesActivity
import com.phngsapplication.app.modules.camera.ui.CameraActivity
import com.phngsapplication.app.modules.detailarticles.`data`.viewmodel.DetailArticlesVM
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import com.phngsapplication.app.modules.loginscreen.ui.LoginScreenActivity
import com.phngsapplication.app.modules.profile.ui.ProfileActivity
import kotlin.String
import kotlin.Unit

class DetailArticlesActivity :
    BaseActivity<ActivityDetailArticlesBinding>(R.layout.activity_detail_articles) {
  private val viewModel: DetailArticlesVM by viewModels<DetailArticlesVM>()

  var imageArticle: ImageView? = null
  var txtTitleArticle: TextView? = null
  var imageAuthor: ImageView? = null
  var txtAuthor: TextView? = null
  var txtDate: TextView? = null
  var txtDescription: TextView? = null

  var Article: ArticlesRowModel? = null

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.detailArticlesVM = viewModel

    //Article = intent.getSerializableExtra("object") as? ArticlesRowModel


  }

  override fun setUpClicks(): Unit {
    binding.btnHome.setOnClickListener {
      val destIntent = HomepageActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnProfile.setOnClickListener {
      val destIntent = ProfileActivity.getIntent(this, null)
      startActivity(destIntent)
    }

    binding.btnAddPlant.setOnClickListener {
      val destIntent = CameraActivity.getIntent(this, null)
      startActivity(destIntent)
    }

    binding.imageBack.setOnClickListener {
      val destIntent = ArticlesActivity.getIntent(this, null)
      startActivity(destIntent)
    }

//    binding.btnHome.setOnClickListener {
//      finish()
//    }
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
