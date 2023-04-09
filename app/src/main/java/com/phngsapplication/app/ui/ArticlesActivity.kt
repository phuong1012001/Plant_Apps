package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.ArticlesAdapter
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityArticlesBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import com.phngsapplication.app.modules.profile.ui.ProfileActivity
import kotlin.String
import kotlin.Unit

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(R.layout.activity_articles) {
  override fun onInitialized(): Unit {
    //Hien thi adapter
    val data = ArrayList<Article>()
    for(i in 1..5){
      data.add(Article("tomato", "Item" + i, "img_ellipse31", "Name" + i, "Date" + i))
    }
    val adapter = ArticlesAdapter(data)

    binding.recyclerArticles.adapter = adapter
    adapter.onItemClick = {
      val intent = Intent(this, DetailArticlesActivity::class.java)
      intent.putExtra("food", it)
      startActivity(intent)
    }
  }
  override fun setUpClicks() {
    binding.btnBack.setOnClickListener {
      val destIntent = HomepageActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnHome.setOnClickListener {
      val destIntent = HomepageActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnProfile.setOnClickListener {
      val destIntent = ProfileActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }
  companion object {
    const val TAG: String = "ARTICLES_ACTIVITY"
    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ArticlesActivity::class.java)
      destIntent.putExtra("ArticlesActivity", bundle)
      return destIntent
    }
  }
}