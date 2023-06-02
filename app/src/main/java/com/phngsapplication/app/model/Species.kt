package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable

data class Species(
    var nameSpecies: String,
    var plants: List<Plant>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(Plant)!!
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameSpecies)
        parcel.writeTypedList(plants)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Species> {
        override fun createFromParcel(parcel: Parcel): Species {
            return Species(parcel)
        }

        override fun newArray(size: Int): Array<Species?> {
            return arrayOfNulls(size)
        }
    }
}