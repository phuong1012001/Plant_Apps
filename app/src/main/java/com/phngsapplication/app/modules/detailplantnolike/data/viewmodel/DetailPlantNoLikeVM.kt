package com.phngsapplication.app.modules.detailplantnolike.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.detailplantnolike.`data`.model.DetailPlantNoLikeModel
import org.koin.core.KoinComponent

class DetailPlantNoLikeVM : ViewModel(), KoinComponent {
  val detailPlantNoLikeModel: MutableLiveData<DetailPlantNoLikeModel> =
      MutableLiveData(DetailPlantNoLikeModel())

  var navArguments: Bundle? = null
}
