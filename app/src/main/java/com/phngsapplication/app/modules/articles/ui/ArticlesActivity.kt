package com.phngsapplication.app.modules.articles.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityArticlesBinding
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesRowModel
import com.phngsapplication.app.modules.articles.`data`.viewmodel.ArticlesVM
import com.phngsapplication.app.modules.detailarticles.ui.DetailArticlesActivity
import com.phngsapplication.app.modules.onboarding1identifyplants.ui.Onboarding1IdentifyPlantsActivity
import kotlin.Int
import kotlin.String
import kotlin.Unit

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(R.layout.activity_articles) {
  private val viewModel: ArticlesVM by viewModels<ArticlesVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val articlesAdapter = ArticlesAdapter(viewModel.articlesList.value?:mutableListOf())
    binding.recyclerArticles.adapter = articlesAdapter
    articlesAdapter.setOnItemClickListener(
    object : ArticlesAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ArticlesRowModel) {
        onClickRecyclerArticles(view, position, item)
      }
    }
    )
    viewModel.articlesList.observe(this) {
      articlesAdapter.updateData(it)
    }
    binding.articlesVM = viewModel
    Handler(Looper.getMainLooper()).postDelayed( {
      val destIntent = Onboarding1IdentifyPlantsActivity.getIntent(this, null)
      startActivity(destIntent)
      finish()
      }, 3000)
    }

    override fun setUpClicks(): Unit {
      binding.imageArrowleft.setOnClickListener {
        finish()
      }
    }

    fun onClickRecyclerArticles(
      view: View,
      position: Int,
      item: ArticlesRowModel
    ): Unit {
      when(view.id) {
        R.id.imageRectangleTwentyTwo -> {
          val destIntent = DetailArticlesActivity.getIntent(this, null)
          startActivity(destIntent)
        }
      }
    }

    companion object {
      const val TAG: String = "ARTICLES_ACTIVITY"


      fun getIntent(context: Context, bundle: Bundle?): Intent {
        val destIntent = Intent(context, ArticlesActivity::class.java)
        destIntent.putExtra("bundle", bundle)
        return destIntent
      }
    }
  }
