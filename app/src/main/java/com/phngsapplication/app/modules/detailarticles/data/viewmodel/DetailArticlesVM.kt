package com.phngsapplication.app.modules.detailarticles.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.detailarticles.`data`.model.DetailArticlesModel
import org.koin.core.KoinComponent

class DetailArticlesVM : ViewModel(), KoinComponent {
  val detailArticlesModel: MutableLiveData<DetailArticlesModel> =
      MutableLiveData(DetailArticlesModel())

  var navArguments: Bundle? = null
}
