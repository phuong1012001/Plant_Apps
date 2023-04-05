package com.phngsapplication.app.modules.listplant.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowListPlantBinding
import com.phngsapplication.app.modules.listplant.`data`.model.ListPlantRowModel
import kotlin.Int
import kotlin.collections.List

class ListPlantAdapter(
  var list: List<ListPlantRowModel>
) : RecyclerView.Adapter<ListPlantAdapter.RowListPlantVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListPlantVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_list_plant,parent,false)
    return RowListPlantVH(view)
  }

  override fun onBindViewHolder(holder: RowListPlantVH, position: Int) {
    val listPlantRowModel = ListPlantRowModel()
    // TODO uncomment following line after integration with data source
    // val listPlantRowModel = list[position]
    holder.binding.listPlantRowModel = listPlantRowModel
  }

  override fun getItemCount(): Int = 4
  // TODO uncomment following line after integration with data source
  // return list.size

//  public fun updateData(newData: List<ListPlantRowModel>) {
//    list = newData
//    notifyDataSetChanged()
//  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: ListPlantRowModel
    ) {
    }
  }

  inner class RowListPlantVH(view: View) : RecyclerView.ViewHolder(view) {


    val binding: RowListPlantBinding = RowListPlantBinding.bind(itemView)
    init {
      binding.linearRowrectangleOne.setOnClickListener {
        // TODO replace with value from datasource
        clickListener?.onItemClick(it, adapterPosition, ListPlantRowModel())
      }
    }
  }
}
