package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.String
data class Plant(
  var imagePlant: String,

  var txtname: String,

  var txtPlant: String,

  var txtKINGDOM: String,

  var txtFAMILY: String,

  var txtDescription: String,

  var imageLike: String,
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(imagePlant)
    parcel.writeString(txtname)
    parcel.writeString(txtPlant)
    parcel.writeString(txtKINGDOM)
    parcel.writeString(txtFAMILY)
    parcel.writeString(txtDescription)
    parcel.writeString(imageLike)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Plant> {
    override fun createFromParcel(parcel: Parcel): Plant {
      return Plant(parcel)
    }

    override fun newArray(size: Int): Array<Plant?> {
      return arrayOfNulls(size)
    }
  }

}
