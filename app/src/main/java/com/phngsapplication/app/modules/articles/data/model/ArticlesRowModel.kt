//package com.phngsapplication.app.modules.articles.`data`.model
//
//import android.os.Parcel
//import android.os.Parcelable
//import kotlin.String
//
//data class ArticlesRowModel(
//  var imageArticle: Int
//  ,
//  var titleArticle: String,
//
//  var imageAuthor: Int,
//
//  var txtAuthor: String,
//
//  var txtDate: String
//) :Parcelable {
//  constructor(parcel: Parcel) : this(
//    parcel.readInt(),
//    parcel.readString()!!,
//    parcel.readInt(),
//    parcel.readString()!!,
//    parcel.readString()!!
//  ) {
//  }
//
//  override fun writeToParcel(parcel: Parcel, flags: Int) {
//    parcel.writeInt(imageArticle)
//    parcel.writeString(titleArticle)
//    parcel.writeInt(imageAuthor)
//    parcel.writeString(txtAuthor)
//    parcel.writeString(txtDate)
//  }
//
//  override fun describeContents(): Int {
//    return 0
//  }
//
//  companion object CREATOR : Parcelable.Creator<ArticlesRowModel> {
//    override fun createFromParcel(parcel: Parcel): ArticlesRowModel {
//      return ArticlesRowModel(parcel)
//    }
//
//    override fun newArray(size: Int): Array<ArticlesRowModel?> {
//      return arrayOfNulls(size)
//    }
//  }
//}

package com.phngsapplication.app.modules.articles.`data`.model

import android.os.Parcel
import android.os.Parcelable
import kotlin.String

data class ArticlesRowModel(
  var imageArticle: String
  ,
  var titleArticle: String,

  var imageAuthor: String,

  var txtAuthor: String,

  var txtDate: String
) :Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(imageArticle)
    parcel.writeString(titleArticle)
    parcel.writeString(imageAuthor)
    parcel.writeString(txtAuthor)
    parcel.writeString(txtDate)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<ArticlesRowModel> {
    override fun createFromParcel(parcel: Parcel): ArticlesRowModel {
      return ArticlesRowModel(parcel)
    }

    override fun newArray(size: Int): Array<ArticlesRowModel?> {
      return arrayOfNulls(size)
    }
  }
}