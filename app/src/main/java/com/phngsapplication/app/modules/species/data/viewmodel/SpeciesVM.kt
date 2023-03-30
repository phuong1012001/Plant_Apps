package com.phngsapplication.app.modules.species.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.species.`data`.model.SpeciesModel
import org.koin.core.KoinComponent

class SpeciesVM : ViewModel(), KoinComponent {
  val speciesModel: MutableLiveData<SpeciesModel> = MutableLiveData(SpeciesModel())

  var navArguments: Bundle? = null
}
