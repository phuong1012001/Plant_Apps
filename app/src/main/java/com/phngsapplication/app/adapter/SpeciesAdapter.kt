package com.phngsapplication.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.model.SpeciesAlphabet
import com.phngsapplication.app.ui.SpeciesFragment

class SpeciesAdapter (
    var list: List<SpeciesAlphabet>
    ) : RecyclerView.Adapter<SpeciesAdapter.RowDSSpeciesVH>() {

    var onItemClick: ((Species)->Unit)? =null   //quan trong
    private lateinit var speciesFragment: SpeciesFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowDSSpeciesVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_ds_species,parent,false)
        return RowDSSpeciesVH(view)
    }

    override fun onBindViewHolder(holder: RowDSSpeciesVH, position: Int) {
        val item = list[position]
        holder.alphabet.setText(item.alphabet)

        val adapter = NameSpeciesAdapter(item.species)
        holder.recyclerView.adapter = adapter

        adapter.onItemClick = {
            onItemClick?.invoke(it)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RowDSSpeciesVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val alphabet: TextView = view.findViewById(R.id.textView)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }
}