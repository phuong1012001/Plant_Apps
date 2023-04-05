package com.phngsapplication.app.modules.articles.ui

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.RowArticlesBinding
import com.phngsapplication.app.modules.articles.`data`.model.ArticlesRowModel
import kotlin.Int
import kotlin.collections.List

class ArticlesAdapter(
  val list: List<ArticlesRowModel>
) : RecyclerView.Adapter<ArticlesAdapter.RowArticlesVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowArticlesVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_articles,parent,false)
    return RowArticlesVH(view)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: RowArticlesVH, position: Int) {
    //val articlesRowModel = ArticlesRowModel()
    val ItemViewModel = list[position]
    holder.imageArticle.setImageResource(ItemViewModel.imageArticle)
    holder.titleArticle.setText(ItemViewModel.titleArticle)
    holder.imageAuthor.setImageResource(ItemViewModel.imageAuthor)
    holder.txtAuthor.setText(ItemViewModel.txtAuthor)
    holder.txtDate.setText(ItemViewModel.txtDate)


    // TODO uncomment following line after integration with data source
    // val articlesRowModel = list[position]
//    holder.binding.articlesRowModel
  }

//  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

//  public fun updateData(newData: List<ArticlesRowModel>) {
//    list = newData
//    notifyDataSetChanged()
//  }

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
    val imageArticle: ImageView = view.findViewById(R.id.imageArticle)
    val titleArticle: TextView = view.findViewById(R.id.titleArticle)
    val imageAuthor: ImageView = view.findViewById(R.id.imageAuthor)
    val txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
    val txtDate: TextView = view.findViewById(R.id.txtDate)
    val binding: RowArticlesBinding = RowArticlesBinding.bind(itemView)
//    init {
//      binding.oneArticle.setOnClickListener {
//        // TODO replace with value from datasource
//        clickListener?.onItemClick(it, adapterPosition, ArticlesRowModel())
//      }
//    }
  }
}
