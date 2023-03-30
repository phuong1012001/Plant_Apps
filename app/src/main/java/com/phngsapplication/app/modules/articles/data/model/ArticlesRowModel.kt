package com.phngsapplication.app.modules.articles.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ArticlesRowModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtDavidAustinW: String? =
      MyApp.getInstance().resources.getString(R.string.msg_david_austin_w)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtShivaniVora: String? = MyApp.getInstance().resources.getString(R.string.lbl_shivani_vora)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txt20190101: String? = MyApp.getInstance().resources.getString(R.string.lbl_2019_01_01)

)
