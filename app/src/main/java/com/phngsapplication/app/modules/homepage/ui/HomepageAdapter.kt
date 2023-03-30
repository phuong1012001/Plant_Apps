package com.phngsapplication.app.modules.homepage.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowHomepageBinding
import com.phngsapplication.app.modules.homepage.`data`.model.HomepageRowModel
import kotlin.Int
import kotlin.collections.List

class HomepageAdapter(
  var list: List<HomepageRowModel>
) : RecyclerView.Adapter<HomepageAdapter.RowHomepageVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHomepageVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_homepage,parent,false)
    return RowHomepageVH(view)
  }

  override fun onBindViewHolder(holder: RowHomepageVH, position: Int) {
    val homepageRowModel = HomepageRowModel()
    // TODO uncomment following line after integration with data source
    // val homepageRowModel = list[position]
    holder.binding.homepageRowModel = homepageRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<HomepageRowModel>) {
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
      item: HomepageRowModel
    ) {
    }
  }

  inner class RowHomepageVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowHomepageBinding = RowHomepageBinding.bind(itemView)
  }
}
