package com.phngsapplication.app.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.GridLayout.Spec
import com.google.firebase.database.DataSnapshot

data class SpeciesAlphabet(
    var alphabet: String,
    var species: List<Species>
) : Parcelable {
    constructor() : this("", emptyList()) // No-argument constructor

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(Species.CREATOR)!!
    )

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

        @JvmStatic
        @JvmName("fromSnapshot")
        fun fromSnapshot(snapshot: DataSnapshot): SpeciesAlphabet? {
            val alphabet = snapshot.child("alphabet").getValue(String::class.java) ?: ""
            val speciesList = snapshot.child("species").children.mapNotNull { it.getValue(Species::class.java) }
            return SpeciesAlphabet(alphabet, speciesList)
        }
    }
}