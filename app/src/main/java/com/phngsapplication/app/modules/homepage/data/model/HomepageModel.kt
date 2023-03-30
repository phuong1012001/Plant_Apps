package com.phngsapplication.app.modules.homepage.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class HomepageModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHelloTaylor: String? = MyApp.getInstance().resources.getString(R.string.lbl_hello_taylor)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHome: String? = MyApp.getInstance().resources.getString(R.string.lbl_home2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLetsLearnMor: String? =
      MyApp.getInstance().resources.getString(R.string.msg_let_s_learn_mor2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_adding_new2)
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
  var txtPlantTypes: String? = MyApp.getInstance().resources.getString(R.string.lbl_plant_types)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPhotography: String? = MyApp.getInstance().resources.getString(R.string.lbl_photography)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtMini: String? = MyApp.getInstance().resources.getString(R.string.lbl_mini)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHomely: String? = MyApp.getInstance().resources.getString(R.string.lbl_homely)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCute: String? = MyApp.getInstance().resources.getString(R.string.lbl_cute)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHOMETwo: String? = MyApp.getInstance().resources.getString(R.string.lbl_home)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPROFILE: String? = MyApp.getInstance().resources.getString(R.string.lbl_profile)

)
