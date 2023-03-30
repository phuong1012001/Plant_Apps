package com.phngsapplication.app.modules.listplant.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.listplant.`data`.model.ListPlantModel
import com.phngsapplication.app.modules.listplant.`data`.model.ListPlantRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class ListPlantVM : ViewModel(), KoinComponent {
  val listPlantModel: MutableLiveData<ListPlantModel> = MutableLiveData(ListPlantModel())

  var navArguments: Bundle? = null

  val listPlantList: MutableLiveData<MutableList<ListPlantRowModel>> =
      MutableLiveData(mutableListOf())
}
