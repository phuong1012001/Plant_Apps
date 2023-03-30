package com.phngsapplication.app.modules.addingnewplant.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.addingnewplant.`data`.model.AddingNewPlantModel
import org.koin.core.KoinComponent

class AddingNewPlantVM : ViewModel(), KoinComponent {
  val addingNewPlantModel: MutableLiveData<AddingNewPlantModel> =
      MutableLiveData(AddingNewPlantModel())

  var navArguments: Bundle? = null
}
