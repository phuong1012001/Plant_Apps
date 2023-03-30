package com.phngsapplication.app.modules.onboarding1identifyplants.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class Onboarding1IdentifyPlantsModel(
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
