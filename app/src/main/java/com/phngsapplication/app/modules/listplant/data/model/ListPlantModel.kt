package com.phngsapplication.app.modules.listplant.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ListPlantModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtCactus: String? = MyApp.getInstance().resources.getString(R.string.lbl_cactus2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCactusOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_cactus2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSearchForCact: String? =
      MyApp.getInstance().resources.getString(R.string.msg_search_for_cact)
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
