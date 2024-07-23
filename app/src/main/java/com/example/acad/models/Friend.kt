package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Friend(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val userId: Long,
    @SerializedName("friend")
    val friendId: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status")
    val status: String
)
