package com.phngsapplication.app.modules.signupscreen.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.signupscreen.`data`.model.SignupScreenModel
import org.koin.core.KoinComponent

class SignupScreenVM : ViewModel(), KoinComponent {
  val signupScreenModel: MutableLiveData<SignupScreenModel> = MutableLiveData(SignupScreenModel())

  var navArguments: Bundle? = null
}
