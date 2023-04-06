package com.phngsapplication.app.modules.homepage.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowHomepageBinding
import com.phngsapplication.app.modules.homepage.`data`.model.PlantRowModel
import kotlin.Int
import kotlin.collections.List

class PlantAdapter(
  var list: List<PlantRowModel>
) : RecyclerView.Adapter<PlantAdapter.RowHomepageVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHomepageVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_homepage,parent,false)
    return RowHomepageVH(view)
  }

  override fun onBindViewHolder(holder: RowHomepageVH, position: Int) {
    val plantRowModel = PlantRowModel()
    // TODO uncomment following line after integration with data source
    // val homepageRowModel = list[position]
    holder.binding.homepageRowModel = plantRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<PlantRowModel>) {
    list = newData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: PlantRowModel
    ) {
    }
  }

  inner class RowHomepageVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowHomepageBinding = RowHomepageBinding.bind(itemView)
  }
}
