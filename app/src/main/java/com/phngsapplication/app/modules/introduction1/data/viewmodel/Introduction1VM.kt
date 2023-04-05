package com.phngsapplication.app.modules.introduction1.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.introduction1.`data`.model.Introduction1Model
import org.koin.core.KoinComponent

class Introduction1VM : ViewModel(), KoinComponent {
  val introduction1Model: MutableLiveData<Introduction1Model> =
      MutableLiveData(Introduction1Model())

  var navArguments: Bundle? = null
}
