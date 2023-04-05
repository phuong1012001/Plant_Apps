package com.phngsapplication.app.modules.introduction1.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class Introduction1Model(
  /**
   * TODO Replace with dynamic value
   */
  var txtGANDUL: String? = MyApp.getInstance().resources.getString(R.string.lbl_gandul)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtIdentifyPlants: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_identify_plants)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_you_can_identif)

)
