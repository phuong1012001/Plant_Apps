package com.phngsapplication.app.modules.forgetpassscreenone.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ForgetPassScreenOneModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtForgetpassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_forget_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtResetlinkwill: String? =
      MyApp.getInstance().resources.getString(R.string.msg_reset_link_will)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEmail: String? = MyApp.getInstance().resources.getString(R.string.lbl_email2)
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
  var etEmailOneValue: String? = null
)
