package com.phngsapplication.app.modules.homepage.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.homepage.`data`.model.HomepageModel
import com.phngsapplication.app.modules.homepage.`data`.model.PlantRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class HomepageVM : ViewModel(), KoinComponent {
  val homepageModel: MutableLiveData<HomepageModel> = MutableLiveData(HomepageModel())

  var navArguments: Bundle? = null

  val homepageList: MutableLiveData<MutableList<PlantRowModel>> =
      MutableLiveData(mutableListOf())
}
