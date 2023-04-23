package com.phngsapplication.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R

class AlphabetAdapter(
    var list: List<String>
) : RecyclerView.Adapter<AlphabetAdapter.RowAlphabetVH>() {

    var onItemClick: ((String)->Unit)? =null   //quan trong

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowAlphabetVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_name,parent,false)
        return RowAlphabetVH(view)
    }

    override fun onBindViewHolder(holder: RowAlphabetVH, position: Int) {
        val item = list[position]
        holder.name.setText(item)

        holder.idName.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RowAlphabetVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val idName: ConstraintLayout = view.findViewById(R.id.idName)
    }
}