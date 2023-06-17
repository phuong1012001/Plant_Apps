package com.phngsapplication.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Plant
import kotlin.Int
import kotlin.collections.List

class PlantAdapter(
  var list: List<Plant>
) : RecyclerView.Adapter<PlantAdapter.RowPlantVH>() {

  var onItemClick: ((Plant)->Unit)? =null   //quan trong

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowPlantVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_plant,parent,false)
    return RowPlantVH(view)
  }

  override fun onBindViewHolder(holder: RowPlantVH, position: Int) {
    val item = list[position]
    holder.titlePlant.setText(item.txtPlant)
    holder.txtKINGDOM.setText(item.txtKINGDOM)
    holder.txtFAMILY.setText(item.txtFAMILY)
    holder.txtDescription.setText(item.txtDescription)

//    val drawableResourceId = holder.itemView.context.resources.getIdentifier(
//      item.imagePlant,
//      "drawable",
//      holder.itemView.context.packageName
//    )

    Glide.with(holder.itemView.context)
      .load(item.imagePlant)
      .into(holder.imagePlant)

    holder.oneSpecie.setOnClickListener{
      onItemClick?.invoke(item)
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  inner class RowPlantVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val imagePlant: ImageView = view.findViewById(R.id.imageSpecie)
    val titlePlant: TextView = view.findViewById(R.id.titleSpecie)
    val txtKINGDOM: TextView = view.findViewById(R.id.txtKINGDOM)
    val txtFAMILY: TextView = view.findViewById(R.id.txtFAMILY)
    val txtDescription: TextView = view.findViewById(R.id.txtDescription)
    val oneSpecie: ConstraintLayout = view.findViewById(R.id.oneSpecie)
  }
}
