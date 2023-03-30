package com.phngsapplication.app.modules.listplant.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ListPlantRowModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtRedCactus: String? = MyApp.getInstance().resources.getString(R.string.lbl_red_cactus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtKINGDOM: String? = MyApp.getInstance().resources.getString(R.string.lbl_kingdom)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFAMILY: String? = MyApp.getInstance().resources.getString(R.string.lbl_family)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPlantae: String? = MyApp.getInstance().resources.getString(R.string.lbl_plantae)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCactaceae: String? = MyApp.getInstance().resources.getString(R.string.lbl_cactaceae)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_description)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_cactus_spines_a)

)
