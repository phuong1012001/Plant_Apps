package com.phngsapplication.app.modules.detailplantnolike.ui

import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityDetailPlantNoLikeBinding
import com.phngsapplication.app.modules.detailplantnolike.`data`.viewmodel.DetailPlantNoLikeVM
import kotlin.String
import kotlin.Unit

class DetailPlantNoLikeActivity :
    BaseActivity<ActivityDetailPlantNoLikeBinding>(R.layout.activity_detail_plant_no_like) {
  private val viewModel: DetailPlantNoLikeVM by viewModels<DetailPlantNoLikeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.detailPlantNoLikeVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "DETAIL_PLANT_NO_LIKE_ACTIVITY"

  }
}
