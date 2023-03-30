package com.phngsapplication.app.modules.articles.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesModel
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class ArticlesVM : ViewModel(), KoinComponent {
  val articlesModel: MutableLiveData<ArticlesModel> = MutableLiveData(ArticlesModel())

  var navArguments: Bundle? = null

  val articlesList: MutableLiveData<MutableList<ArticlesRowModel>> =
      MutableLiveData(mutableListOf())
}
