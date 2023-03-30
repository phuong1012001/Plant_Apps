package com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.onboarding3readmanyarticlesaboutplants.`data`.model.Onboarding3ReadManyArticlesAboutPlantsModel
import org.koin.core.KoinComponent

class Onboarding3ReadManyArticlesAboutPlantsVM : ViewModel(), KoinComponent {
  val onboarding3ReadManyArticlesAboutPlantsModel:
      MutableLiveData<Onboarding3ReadManyArticlesAboutPlantsModel> =
      MutableLiveData(Onboarding3ReadManyArticlesAboutPlantsModel())

  var navArguments: Bundle? = null
}
