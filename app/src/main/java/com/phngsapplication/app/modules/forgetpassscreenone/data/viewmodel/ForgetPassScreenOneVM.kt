package com.phngsapplication.app.modules.forgetpassscreenone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.forgetpassscreenone.`data`.model.ForgetPassScreenOneModel
import org.koin.core.KoinComponent

class ForgetPassScreenOneVM : ViewModel(), KoinComponent {
  val forgetPassScreenOneModel: MutableLiveData<ForgetPassScreenOneModel> =
      MutableLiveData(ForgetPassScreenOneModel())

  var navArguments: Bundle? = null
}
