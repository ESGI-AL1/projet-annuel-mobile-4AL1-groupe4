package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Friend(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val userId: Int,
    @SerializedName("friend")
    val friendId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status")
    val status: String
)
