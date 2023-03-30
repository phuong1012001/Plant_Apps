package com.phngsapplication.app.modules.articles.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowArticlesBinding
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesRowModel
import kotlin.Int
import kotlin.collections.List

class ArticlesAdapter(
  var list: List<ArticlesRowModel>
) : RecyclerView.Adapter<ArticlesAdapter.RowArticlesVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowArticlesVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_articles,parent,false)
    return RowArticlesVH(view)
  }

  override fun onBindViewHolder(holder: RowArticlesVH, position: Int) {
    val articlesRowModel = ArticlesRowModel()
    // TODO uncomment following line after integration with data source
    // val articlesRowModel = list[position]
    holder.binding.articlesRowModel = articlesRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ArticlesRowModel>) {
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
      item: ArticlesRowModel
    ) {
    }
  }

  inner class RowArticlesVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowArticlesBinding = RowArticlesBinding.bind(itemView)
    init {
      binding.imageRectangleTwentyTwo.setOnClickListener {
        // TODO replace with value from datasource
        clickListener?.onItemClick(it, adapterPosition, ArticlesRowModel())
      }
    }
  }
}
