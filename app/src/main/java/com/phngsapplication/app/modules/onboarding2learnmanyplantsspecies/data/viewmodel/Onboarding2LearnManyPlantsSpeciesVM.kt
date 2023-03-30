package com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phngsapplication.app.modules.onboarding2learnmanyplantsspecies.`data`.model.Onboarding2LearnManyPlantsSpeciesModel
import org.koin.core.KoinComponent

class Onboarding2LearnManyPlantsSpeciesVM : ViewModel(), KoinComponent {
  val onboarding2LearnManyPlantsSpeciesModel:
      MutableLiveData<Onboarding2LearnManyPlantsSpeciesModel> =
      MutableLiveData(Onboarding2LearnManyPlantsSpeciesModel())

  var navArguments: Bundle? = null
}
