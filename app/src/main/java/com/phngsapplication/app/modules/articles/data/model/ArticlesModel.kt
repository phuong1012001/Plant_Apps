package com.phngsapplication.app.modules.articles.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ArticlesModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtArticles: String? = MyApp.getInstance().resources.getString(R.string.lbl_articles)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtArticlesOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_articles)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSearchForArti: String? =
      MyApp.getInstance().resources.getString(R.string.msg_search_for_arti)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHOMEOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_home)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPROFILE: String? = MyApp.getInstance().resources.getString(R.string.lbl_profile)

)
