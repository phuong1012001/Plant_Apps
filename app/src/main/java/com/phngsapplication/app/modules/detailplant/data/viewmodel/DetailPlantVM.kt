package com.phngsapplication.app.modules.detailplant.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.detailplant.`data`.model.DetailPlantModel
import org.koin.core.KoinComponent

class DetailPlantVM : ViewModel(), KoinComponent {
  val detailPlantModel: MutableLiveData<DetailPlantModel> = MutableLiveData(DetailPlantModel())

  var navArguments: Bundle? = null
}
