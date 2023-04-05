package com.phngsapplication.app.modules.introduction3.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.introduction3.`data`.model.Introduction3Model
import org.koin.core.KoinComponent

class Introduction3VM : ViewModel(), KoinComponent {
  val introduction3Model:
      MutableLiveData<Introduction3Model> =
      MutableLiveData(Introduction3Model())

  var navArguments: Bundle? = null
}
