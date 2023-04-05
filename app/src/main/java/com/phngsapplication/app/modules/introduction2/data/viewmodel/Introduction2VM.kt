package com.phngsapplication.app.modules.introduction2.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.introduction2.`data`.model.Introduction2Model
import org.koin.core.KoinComponent

class Introduction2VM : ViewModel(), KoinComponent {
  val introduction2Model:
      MutableLiveData<Introduction2Model> =
      MutableLiveData(Introduction2Model())

  var navArguments: Bundle? = null
}
