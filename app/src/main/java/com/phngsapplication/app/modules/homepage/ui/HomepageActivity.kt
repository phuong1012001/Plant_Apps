package com.phngsapplication.app.modules.homepage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.phngsapplication.app.databinding.ActivityHomepageBinding
import com.phngsapplication.app.ui.ArticlesActivity
//import com.phngsapplication.app.ui.ArticlesActivity
import com.phngsapplication.app.modules.camera.ui.CameraActivity
import com.phngsapplication.app.modules.homepage.`data`.model.PlantRowModel
import com.phngsapplication.app.modules.homepage.`data`.viewmodel.HomepageVM
import com.phngsapplication.app.modules.profile.ui.ProfileActivity
import com.phngsapplication.app.ui.ListPlantActivity
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit

class HomepageActivity : BaseActivity<ActivityHomepageBinding>(R.layout.activity_homepage) {
  private val viewModel: HomepageVM by viewModels<HomepageVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")

    val plantAdapter = PlantAdapter(viewModel.homepageList.value?:mutableListOf())
    binding.recyclerHomepage.adapter = plantAdapter
    plantAdapter.setOnItemClickListener(
    object : PlantAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : PlantRowModel) {
        onClickRecyclerHomepage(view, position, item)
      }
    }
    )
    viewModel.homepageList.observe(this) {
      plantAdapter.updateData(it)
    }
    binding.homepageVM = viewModel
    setUpSearchViewGroupTwentyOneListener()
  }

  override fun setUpClicks(): Unit {
      binding.imageCamera.setOnClickListener {
        ImagePickerFragmentDialog().show(supportFragmentManager)
        { path ->//TODO HANDLE DATA
        }
      }
      binding.linearColumncut.setOnClickListener {
        val destIntent = ListPlantActivity.getIntent(this, null)
        startActivity(destIntent)
      }
      binding.linearColumngroup.setOnClickListener {
        val destIntent = ArticlesActivity.getIntent(this, null)
        startActivity(destIntent)
      }
      binding.linearColumncamera.setOnClickListener {
        val destIntent = CameraActivity.getIntent(this, null)
        startActivity(destIntent)
      }
      binding.linearColumnuser.setOnClickListener{
        val destIntent = ProfileActivity.getIntent(this, null)
        startActivity(destIntent)
      }
    }

    fun onClickRecyclerHomepage(
      view: View,
      position: Int,
      item: PlantRowModel
    ): Unit {
      when(view.id) {
      }
    }

    private fun setUpSearchViewGroupTwentyOneListener(): Unit {
      binding.searchViewGroupTwentyOne.setOnQueryTextListener(object :
      SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0 : String) : Boolean {
          // Performs search when user hit
          // the search button on the keyboard
          return false
        }
        override fun onQueryTextChange(p0 : String) : Boolean {
          // Start filtering the list as user
          // start entering the characters
          return false
        }
        })
      }

      companion object {
        const val TAG: String = "HOMEPAGE_ACTIVITY"

          fun getIntent(context: Context, bundle: Bundle?): Intent {
              val destIntent = Intent(context, HomepageActivity::class.java)
              destIntent.putExtra("bundle", bundle)
              return destIntent
          }

      }
    }
