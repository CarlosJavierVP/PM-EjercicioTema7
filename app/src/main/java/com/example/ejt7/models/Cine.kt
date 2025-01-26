package com.example.ejt7.models

import android.os.Parcel
import android.os.Parcelable

class Cine (var id:Int, var nombreCine:String, var ciudad:Ciudad, var latitud:Double, var longitud:Double ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        TODO("ciudad"),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(nombreCine)
        p0.writeString(ciudad.toString())
        p0.writeDouble(latitud)
        p0.writeDouble(longitud)
    }

    companion object CREATOR : Parcelable.Creator<Cine> {
        override fun createFromParcel(parcel: Parcel): Cine {
            return Cine(parcel)
        }

        override fun newArray(size: Int): Array<Cine?> {
            return arrayOfNulls(size)
        }
    }
}