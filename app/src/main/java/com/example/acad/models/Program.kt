package com.example.acad.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Program(
    val id: Int,
    val file: String,
    @SerializedName("created_at")
    val createdAt: String,
    val isVisible: Boolean,
    val title: String,
    val date: String,
    val description: String,
    val tags: ArrayList<String>?,
    val profileImage: Int,
    val code: String,
    val author: Any?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        file = parcel.readString().toString(),
        createdAt = parcel.readString().toString(),
        title = parcel.readString().toString(),
        tags = parcel.createStringArrayList(),
        profileImage = parcel.readInt(),
        code = parcel.readString().toString(),
        description = parcel.readString().toString(),
        author = parcel.readValue(User::class.java.classLoader),
        isVisible = parcel.readByte() != 0.toByte(),
        date = parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(createdAt)
        parcel.writeString(description)
        parcel.writeStringList(tags)
        parcel.writeInt(profileImage)
        parcel.writeString(code)
        parcel.writeValue(author)
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

data class ShowProgram(
    val id: Int,
    val file: String,
    @SerializedName("created_at")
    val createdAt: String,
    val isVisible: Boolean,
    val title: String,
    val date: String,
    val description: String,
    val tags: ArrayList<String>?,
    val profileImage: Int,
    val code: String,
    val author: User,
)