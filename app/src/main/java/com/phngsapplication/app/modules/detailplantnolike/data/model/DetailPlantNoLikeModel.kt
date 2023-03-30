package com.phngsapplication.app.modules.detailplantnolike.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class DetailPlantNoLikeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtCircleCactus: String? = MyApp.getInstance().resources.getString(R.string.lbl_circle_cactus)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_4_1)
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
  var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.msg_the_word_cactu)
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
