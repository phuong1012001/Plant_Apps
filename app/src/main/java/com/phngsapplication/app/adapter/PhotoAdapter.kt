package com.phngsapplication.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Photo

class PhotoAdapter (var list: List<Photo>
): RecyclerView.Adapter<PhotoAdapter.PhotoVH>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_photography,parent,false)
        return PhotoVH(view)
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val item = list[position]
        holder.txt.setText("#"+item.text)

        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
        item.image,
        "drawable",
        holder.itemView.context.packageName
        )

        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PhotoVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val txt: TextView = view.findViewById(R.id.txtImage)
        val image: ImageView = view.findViewById(R.id.image)

    }
}