package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.GridLayout.Spec

data class SpeciesAlphabet(
    var alphabet: String,
    var species: List<Species>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(Species)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alphabet)
        parcel.writeTypedList(species)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpeciesAlphabet> {
        override fun createFromParcel(parcel: Parcel): SpeciesAlphabet {
            return SpeciesAlphabet(parcel)
        }

        override fun newArray(size: Int): Array<SpeciesAlphabet?> {
            return arrayOfNulls(size)
        }
    }

}
