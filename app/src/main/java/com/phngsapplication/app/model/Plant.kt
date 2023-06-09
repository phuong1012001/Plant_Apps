package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable
import kotlin.String

data class Plant(
  var plantId: String,

  var speciesId: String,

  var speciesName: String,

  var imagePlant: String,

  var txtPlant: String,

  var txtKINGDOM: String,

  var txtFAMILY: String,

  var txtDescription: String,

  var txtCharacterOne: String,

  var txtCharacterTwo: String,

  var imageLike: String,
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readString()!!,
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
    parcel.writeString(plantId)
    parcel.writeString(speciesId)
    parcel.writeString(speciesName)
    parcel.writeString(imagePlant)
    parcel.writeString(txtPlant)
    parcel.writeString(txtKINGDOM)
    parcel.writeString(txtFAMILY)
    parcel.writeString(txtDescription)
    parcel.writeString(txtCharacterOne)
    parcel.writeString(txtCharacterTwo)
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
