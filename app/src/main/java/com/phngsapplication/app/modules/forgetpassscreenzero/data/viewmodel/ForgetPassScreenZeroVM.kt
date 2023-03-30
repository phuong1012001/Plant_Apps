package com.phngsapplication.app.modules.forgetpassscreenzero.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.forgetpassscreenzero.`data`.model.ForgetPassScreenZeroModel
import org.koin.core.KoinComponent

class ForgetPassScreenZeroVM : ViewModel(), KoinComponent {
  val forgetPassScreenZeroModel: MutableLiveData<ForgetPassScreenZeroModel> =
      MutableLiveData(ForgetPassScreenZeroModel())

  var navArguments: Bundle? = null
}
