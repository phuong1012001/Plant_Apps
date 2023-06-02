//package com.phngsapplication.app.model
//
//import android.os.Parcel
//import android.os.Parcelable
//
//data class SpeciesDB(
//    var uid: String?,
//    var species: String?,
//    var id: String?,
//    var timestamp: Long
//): Parcelable{
//    constructor(s: String, emptyList: List<Any>) : this("", emptyList()) // No-argument constructor
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readLong()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(uid)
//        parcel.writeString(species)
//        parcel.writeString(id)
//        parcel.writeLong(timestamp)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<SpeciesDB> {
//        override fun createFromParcel(parcel: Parcel): SpeciesDB {
//            return SpeciesDB(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SpeciesDB?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
