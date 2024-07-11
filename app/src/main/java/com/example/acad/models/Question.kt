package com.example.acad.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Question(
    val profileImage: Int, // Assuming you are using a drawable resource ID
    val authorName: String,
    @SerializedName("created_at")
    val questionTime: String,
    @SerializedName("title")
    val questionTitle: String,
    @SerializedName("description")
    val questionDescription: String,
    val tags: List<String>,
    @SerializedName("views_count")
    val viewsCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int,
    @SerializedName("votes_count")
    val votesCount: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(profileImage)
        parcel.writeString(authorName)
        parcel.writeString(questionTime)
        parcel.writeString(questionTitle)
        parcel.writeString(questionDescription)
        parcel.writeStringList(tags)
        parcel.writeInt(viewsCount)
        parcel.writeInt(commentsCount)
        parcel.writeInt(votesCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
