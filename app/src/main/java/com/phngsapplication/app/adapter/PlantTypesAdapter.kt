package com.phngsapplication.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowPlantTypesBinding
import com.phngsapplication.app.modules.homepage.`data`.model.HomepageRowModel
import kotlin.Int
import kotlin.collections.List

class PlantTypesAdapter(
    var list: List<HomepageRowModel>
) : RecyclerView.Adapter<PlantTypesAdapter.RowHomepageVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHomepageVH {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_plant_types,parent,false)
        return RowHomepageVH(view)
    }

    override fun onBindViewHolder(holder: RowHomepageVH, position: Int) {
        val homepageRowModel = HomepageRowModel()

    }

    override fun getItemCount(): Int {
        return list.size
    }

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
        val binding: RowPlantTypesBinding = RowPlantTypesBinding.bind(itemView)
        init {
            binding.plantTypes.setOnClickListener {
                // TODO replace with value from datasource
                clickListener?.onItemClick(it, adapterPosition, HomepageRowModel())
            }
        }
    }
}