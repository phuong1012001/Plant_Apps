package com.phngsapplication.app.modules.onboarding1identifyplants.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.onboarding1identifyplants.`data`.model.Onboarding1IdentifyPlantsModel
import org.koin.core.KoinComponent

class Onboarding1IdentifyPlantsVM : ViewModel(), KoinComponent {
  val onboarding1IdentifyPlantsModel: MutableLiveData<Onboarding1IdentifyPlantsModel> =
      MutableLiveData(Onboarding1IdentifyPlantsModel())

  var navArguments: Bundle? = null
}
