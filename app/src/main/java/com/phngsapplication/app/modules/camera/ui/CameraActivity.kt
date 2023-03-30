package com.phngsapplication.app.modules.camera.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityCameraBinding
import com.phngsapplication.app.modules.addingnewplant.ui.AddingNewPlantActivity
import com.phngsapplication.app.modules.camera.`data`.viewmodel.CameraVM
import kotlin.String
import kotlin.Unit

class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {
  private val viewModel: CameraVM by viewModels<CameraVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.cameraVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.viewEllipseThree.setOnClickListener {
      val destIntent = AddingNewPlantActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "CAMERA_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, CameraActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
