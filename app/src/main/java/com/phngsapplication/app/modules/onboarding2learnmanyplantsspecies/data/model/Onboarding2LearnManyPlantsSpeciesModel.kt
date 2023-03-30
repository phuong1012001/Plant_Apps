package com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class Onboarding2LearnManyPlantsSpeciesModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtWELOK: String? = MyApp.getInstance().resources.getString(R.string.lbl_welok)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLearnManyPlan: String? =
      MyApp.getInstance().resources.getString(R.string.msg_learn_many_plan)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_let_s_learn_abo)

)
