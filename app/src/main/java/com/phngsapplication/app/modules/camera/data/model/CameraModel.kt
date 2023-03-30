package com.phngsapplication.app.modules.camera.`data`.model

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class CameraModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtAUTO: String? = MyApp.getInstance().resources.getString(R.string.lbl_auto)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPHOTO: String? = MyApp.getInstance().resources.getString(R.string.lbl_photo)

)
