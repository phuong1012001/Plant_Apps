package com.phngsapplication.app.modules.forgetpassscreenzero.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ForgetPassScreenZeroModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtForgetpassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_forget_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPasswordhasbe: String? =
      MyApp.getInstance().resources.getString(R.string.msg_password_has_be)

)
