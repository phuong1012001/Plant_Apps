package com.phngsapplication.app.modules.introduction3.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class Introduction3Model(
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
