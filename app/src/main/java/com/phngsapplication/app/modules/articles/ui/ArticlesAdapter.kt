package com.phngsapplication.app.modules.articles.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.modules.articles.data.model.ArticlesRowModel

class ArticlesAdapter(
  val list: List<ArticlesRowModel>
) : RecyclerView.Adapter<ArticlesAdapter.RowArticlesVH>() {

  var onItemClick: ((ArticlesRowModel)->Unit)? =null   //quan trong
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowArticlesVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_articles,parent,false)
    return RowArticlesVH(view)
  }

  override fun onBindViewHolder(holder: RowArticlesVH, position: Int) {
    val ItemViewModel = list[position]
    holder.titleArticle.setText(ItemViewModel.titleArticle)
    holder.txtAuthor.setText(ItemViewModel.txtAuthor)
    holder.txtDate.setText(ItemViewModel.txtDate)


    val drawableResourceId1 = holder.itemView.context.resources.getIdentifier(
      ItemViewModel.imageArticle,
      "drawable",
      holder.itemView.context.packageName
    )

    Glide.with(holder.itemView.context)
      .load(drawableResourceId1)
      .into(holder.imageArticle)


    val drawableResourceId2 = holder.itemView.context.resources.getIdentifier(
      ItemViewModel.imageAuthor,
      "drawable",
      holder.itemView.context.packageName
    )

    Glide.with(holder.itemView.context)
      .load(drawableResourceId2)
      .into(holder.imageAuthor)

    holder.oneArticle.setOnClickListener{
      onItemClick?.invoke(ItemViewModel)
    }
  }
  override fun getItemCount(): Int {
    return list.size
  }
  inner class RowArticlesVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val imageArticle: ImageView = view.findViewById(R.id.imageArticle)
    val titleArticle: TextView = view.findViewById(R.id.titleArticle)
    val imageAuthor: ImageView = view.findViewById(R.id.imageAuthor)
    val txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
    val txtDate: TextView = view.findViewById(R.id.txtDate)
    val oneArticle: ConstraintLayout = view.findViewById(R.id.oneArticle)
  }
}
