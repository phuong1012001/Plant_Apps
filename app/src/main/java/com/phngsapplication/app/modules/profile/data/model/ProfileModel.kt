package com.phngsapplication.app.modules.profile.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ProfileModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtTaylorSwift: String? = MyApp.getInstance().resources.getString(R.string.lbl_taylor_swift)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLosAngelesCa: String? =
      MyApp.getInstance().resources.getString(R.string.msg_los_angeles_ca)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSPECIES: String? = MyApp.getInstance().resources.getString(R.string.lbl_species)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtARTICLES: String? = MyApp.getInstance().resources.getString(R.string.lbl_articles2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtYourCollected: String? =
      MyApp.getInstance().resources.getString(R.string.msg_your_collected)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtAlagatrePlant: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_alagatre_plant)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txt02012019: String? = MyApp.getInstance().resources.getString(R.string.lbl_02_01_2019)
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
  ,
  /**
   * TODO Replace with dynamic value
   */
  var et1TBDValue: String? = null
)
