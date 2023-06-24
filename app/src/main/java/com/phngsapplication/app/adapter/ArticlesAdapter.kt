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
import com.phngsapplication.app.model.Article
import java.util.*

class ArticlesAdapter(
  val list: List<Article>
) : RecyclerView.Adapter<ArticlesAdapter.RowArticlesVH>() {

  var onItemClick: ((Article)->Unit)? =null   //quan trong
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowArticlesVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_articles,parent,false)
    return RowArticlesVH(view)
  }

  override fun onBindViewHolder(holder: RowArticlesVH, position: Int) {
    val ItemViewModel = list[position]
    holder.titleArticle.setText(ItemViewModel.titleArticle)
    holder.txtAuthor.setText(ItemViewModel.txtAuthor)
    holder.txtDate.setText(getShortDate(ItemViewModel.txtDate.toLong()))

//    val drawableResourceId1 = holder.itemView.context.resources.getIdentifier(
//      ItemViewModel.imageArticle,
//      "drawable",
//      holder.itemView.context.packageName
//    )
//
//    Glide.with(holder.itemView.context)
//      .load(drawableResourceId1)
//      .into(holder.imageArticle)


    Glide.with(holder.itemView.context)
      .load(ItemViewModel.imageArticle)
      .into(holder.imageArticle)

    Glide.with(holder.itemView.context)
      .load(ItemViewModel.imageAuthor)
      .into(holder.imageAuthor)

    holder.oneArticle.setOnClickListener{
      onItemClick?.invoke(ItemViewModel)
    }
  }

  private fun getShortDate(ts:Long?):String{
    if(ts == null) return ""
    //Get instance of calendar
    val calendar = Calendar.getInstance(Locale.getDefault())
    //get current date from ts
    calendar.timeInMillis = ts
    //return formatted date
    return android.text.format.DateFormat.format("yyyy. MM. dd", calendar).toString()
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
