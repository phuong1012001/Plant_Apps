package com.phngsapplication.app.modules.listplant.ui

import android.view.View
import androidx.activity.viewModels
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityListPlantBinding
import com.phngsapplication.app.modules.detailplant.ui.DetailPlantActivity
import com.phngsapplication.app.modules.listplant.`data`.model.ListPlantRowModel
import com.phngsapplication.app.modules.listplant.`data`.viewmodel.ListPlantVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class ListPlantActivity : BaseActivity<ActivityListPlantBinding>(R.layout.activity_list_plant) {
  private val viewModel: ListPlantVM by viewModels<ListPlantVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listPlantAdapter = ListPlantAdapter(viewModel.listPlantList.value?:mutableListOf())
    binding.recyclerListPlant.adapter = listPlantAdapter
    listPlantAdapter.setOnItemClickListener(
    object : ListPlantAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ListPlantRowModel) {
        onClickRecyclerListPlant(view, position, item)
      }
    }
    )
//    viewModel.listPlantList.observe(this) {
//      listPlantAdapter.updateData(it)
//    }
    binding.listPlantVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  fun onClickRecyclerListPlant(
    view: View,
    position: Int,
    item: ListPlantRowModel
  ): Unit {
    when(view.id) {
      R.id.linearRowrectangleOne ->  {
        val destIntent = DetailPlantActivity.getIntent(this, null)
        startActivity(destIntent)
      }
    }
  }

  companion object {
    const val TAG: String = "LIST_PLANT_ACTIVITY"

  }
}
