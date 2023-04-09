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

class ListPlantAdapter(
  var list: List<Plant>
) : RecyclerView.Adapter<ListPlantAdapter.RowListPlantVH>() {

  var onItemClick: ((Plant)->Unit)? =null   //quan trong

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListPlantVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_list_plant,parent,false)
    return RowListPlantVH(view)
  }

  override fun onBindViewHolder(holder: RowListPlantVH, position: Int) {
    val item = list[position]
    holder.titleSpecie.setText(item.titleSpecie)
    holder.txtKINGDOM.setText(item.txtKINGDOM)
    holder.txtFAMILY.setText(item.txtFAMILY)
    holder.txtDescription.setText(item.txtDescription)

    val drawableResourceId = holder.itemView.context.resources.getIdentifier(
      item.imageSpecie,
      "drawable",
      holder.itemView.context.packageName
    )

    Glide.with(holder.itemView.context)
      .load(drawableResourceId)
      .into(holder.imageSpecie)

    holder.oneSpecie.setOnClickListener{
      onItemClick?.invoke(item)
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  inner class RowListPlantVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val imageSpecie: ImageView = view.findViewById(R.id.imageSpecie)
    val titleSpecie: TextView = view.findViewById(R.id.titleSpecie)
    val txtKINGDOM: TextView = view.findViewById(R.id.txtKINGDOM)
    val txtFAMILY: TextView = view.findViewById(R.id.txtFAMILY)
    val txtDescription: TextView = view.findViewById(R.id.txtDescription)
    val oneSpecie: ConstraintLayout = view.findViewById(R.id.oneSpecie)
  }
}
