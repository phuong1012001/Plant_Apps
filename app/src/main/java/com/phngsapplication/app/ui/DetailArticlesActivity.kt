package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailArticlesBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.modules.camera.ui.CameraActivity
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import com.phngsapplication.app.modules.profile.ui.ProfileActivity

class DetailArticlesActivity :
    BaseActivity<ActivityDetailArticlesBinding>(R.layout.activity_detail_articles) {
//  private val viewModel: DetailArticlesVM by viewModels<DetailArticlesVM>()

  override fun onInitialized(): Unit {
//    viewModel.navArguments = intent.extras?.getBundle("bundle")
//    binding.detailArticlesVM = viewModel

    val Article = intent.getParcelableExtra<Article>("food")
    if(Article != null){
      binding.txtAuthor.setText(Article.txtAuthor)
      binding.txtDate.setText(Article.txtDate)


      val drawableResourceId1 = this.resources.getIdentifier(Article.imageArticle,
        "drawable",
        this.packageName)


      Glide.with(this)
        .load(drawableResourceId1)
        .into(binding.imageArticle)

      val drawableResourceId2 = this.resources.getIdentifier(Article.imageAuthor,
        "drawable",
        this.packageName)


      Glide.with(this)
        .load(drawableResourceId2)
        .into(binding.imageAuthor)
    }
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