package com.phngsapplication.app.modules.addingnewplant.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class AddingNewPlantModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtAddingNew: String? = MyApp.getInstance().resources.getString(R.string.lbl_adding_new)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txt1TBD: String? = MyApp.getInstance().resources.getString(R.string.lbl_1_tbd)
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
