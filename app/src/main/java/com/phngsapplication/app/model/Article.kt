package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable
import kotlin.String

data class Article(
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

  companion object CREATOR : Parcelable.Creator<Article> {
    override fun createFromParcel(parcel: Parcel): Article {
      return Article(parcel)
    }

    override fun newArray(size: Int): Array<Article?> {
      return arrayOfNulls(size)
    }
  }
}