package com.phngsapplication.app.modules.articles.`data`.model

import android.media.Image
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ArticlesRowModel(
  var imageArticle: Int
//      MyApp.getInstance().resources.getString(R.string.msg_david_austin_w)
  ,
  var titleArticle: String ,

  var imageAuthor: Int ,

  var txtAuthor: String ,

  var txtDate: String
)
