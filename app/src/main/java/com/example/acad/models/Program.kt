package com.example.acad.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class Program(
    val id: Int,
    val title: String,
    val date: String,
    val description: String,
    val tags: ArrayList<String>?,
    val profileImage: Int,
    val code: String,
    val author: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeStringList(tags)
        parcel.writeInt(profileImage)
        parcel.writeString(code)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Program> {
        override fun createFromParcel(parcel: Parcel): Program {
            return Program(parcel)
        }

        override fun newArray(size: Int): Array<Program?> {
            return arrayOfNulls(size)
        }
    }
}