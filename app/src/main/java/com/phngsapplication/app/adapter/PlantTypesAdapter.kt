package com.phngsapplication.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowPlantTypesBinding
import com.phngsapplication.app.model.PlantTypes
import kotlin.Int
import kotlin.collections.List

class PlantTypesAdapter(
    var list: List<PlantTypes>
) : RecyclerView.Adapter<PlantTypesAdapter.RowHomepageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHomepageVH {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_plant_types,parent,false)
        return RowHomepageVH(view)
    }

    override fun onBindViewHolder(holder: RowHomepageVH, position: Int) {
        val item = list[position]
        holder.txtPlantTypes.setText(item.nameTxt)
        holder.txtNumber.setText(item.numberTxt.toString() + " Types of Plants")
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class RowHomepageVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowPlantTypesBinding = RowPlantTypesBinding.bind(itemView)

        val txtPlantTypes: TextView = view.findViewById(R.id.txtTypePlant)
        val txtNumber: TextView = view.findViewById(R.id.txtNumberTypes)
    }
}