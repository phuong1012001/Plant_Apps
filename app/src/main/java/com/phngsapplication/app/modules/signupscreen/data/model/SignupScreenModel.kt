package com.phngsapplication.app.modules.signupscreen.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class SignupScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtSignUp: String? = MyApp.getInstance().resources.getString(R.string.lbl_signup2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRegisternewac: String? =
      MyApp.getInstance().resources.getString(R.string.msg_register_new_ac)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEmail: String? = MyApp.getInstance().resources.getString(R.string.lbl_email)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txt2019Planthink: String? =
      MyApp.getInstance().resources.getString(R.string.msg_2019_planthink)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etEmailOneValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupNinetyValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupNinetyTwoValue: String? = null
)
