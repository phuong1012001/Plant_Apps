package com.phngsapplication.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.Spec
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species

class NameSpeciesAdapter(
    var list: List<Species>
) : RecyclerView.Adapter<NameSpeciesAdapter.RowSpeciesVH>() {

    var onItemClick: ((Species)->Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowSpeciesVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_name,parent,false)
        return RowSpeciesVH(view)
    }

    override fun onBindViewHolder(holder: RowSpeciesVH, position: Int) {
        val item = list[position]
        holder.name.setText(item.nameSpecies)

        holder.idName.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RowSpeciesVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val idName: ConstraintLayout = view.findViewById(R.id.idName)
    }
}