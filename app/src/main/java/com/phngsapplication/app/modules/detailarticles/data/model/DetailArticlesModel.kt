package com.phngsapplication.app.modules.detailarticles.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class DetailArticlesModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtEvenonUrbanE: String? =
      MyApp.getInstance().resources.getString(R.string.msg_even_on_urban_e2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtShyllaMonic: String? = MyApp.getInstance().resources.getString(R.string.lbl_shylla_monic)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txt20190101: String? = MyApp.getInstance().resources.getString(R.string.lbl_2019_01_01)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFollow: String? = MyApp.getInstance().resources.getString(R.string.lbl_follow)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_public_parks_as)
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
