package com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class Onboarding3ReadManyArticlesAboutPlantsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtPLANT: String? = MyApp.getInstance().resources.getString(R.string.lbl_plant)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSUKET: String? = MyApp.getInstance().resources.getString(R.string.lbl_suket)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtWELOK: String? = MyApp.getInstance().resources.getString(R.string.lbl_welok)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtReadManyArtic: String? =
      MyApp.getInstance().resources.getString(R.string.msg_read_many_artic)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_let_s_learn_mor)

)
