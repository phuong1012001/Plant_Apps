package com.phngsapplication.app.modules.loginscreen.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class LoginScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHello: String? = MyApp.getInstance().resources.getString(R.string.lbl_hello)
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
  var txtEmail: String? = MyApp.getInstance().resources.getString(R.string.lbl_email)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtForgotPassword: String? =
      MyApp.getInstance().resources.getString(R.string.msg_forgot_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDontHaveAcco: String? =
      MyApp.getInstance().resources.getString(R.string.msg_don_t_have_acco)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSignUp: String? = MyApp.getInstance().resources.getString(R.string.lbl_sign_up)
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
  var etGroupEightyFiveValue: String? = null
)
