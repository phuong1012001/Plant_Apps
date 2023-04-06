package com.phngsapplication.app.modules.articles.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.phngsapplication.app.databinding.ActivityArticlesBinding
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesRowModel
import com.phngsapplication.app.modules.articles.data.viewmodel.ArticlesVM
import com.phngsapplication.app.modules.detailarticles.ui.DetailArticlesActivity
import com.phngsapplication.app.modules.homepage.ui.HomepageActivity
import com.phngsapplication.app.modules.species.ui.SpeciesActivity
import kotlin.String
import kotlin.Unit

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(R.layout.activity_articles) {
  override fun onInitialized(): Unit {
    //Hien thi adapter
    val data = ArrayList<ArticlesRowModel>()
    for(i in 1..5){
      data.add(ArticlesRowModel("img_rectangle22", "Item" + i, "img_ellipse31", "Name" + i, "Date" + i))
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
    binding.imageArrowleft.setOnClickListener {
      val destIntent = HomepageActivity.getIntent(this, null)
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