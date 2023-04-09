package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityListPlantBinding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.adapter.ListPlantAdapter
import kotlin.String
import kotlin.Unit

class ListPlantActivity : BaseActivity<ActivityListPlantBinding>(R.layout.activity_list_plant){

  override fun onInitialized(): Unit {
    //Hien thi adapter
    val data = ArrayList<Plant>()
    for(i in 1..5){
      data.add(Plant("img_rectangle_3", "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
    }
    val adapter = ListPlantAdapter(data)
    binding.recyclerArticles.adapter = adapter

    adapter.onItemClick = {
      val intent = Intent(this, DetailPlantActivity::class.java)
      intent.putExtra("specie", it)
      startActivity(intent)
    }
  }

  override fun setUpClicks(): Unit {
//    binding.imageArrowleft.setOnClickListener {
//      finish()
//    }
  }

  companion object {
    const val TAG: String = "LIST_PLANT_ACTIVITY"

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ListPlantActivity::class.java)
      destIntent.putExtra("ListPlantActivity", bundle)
      return destIntent
    }
  }
}
