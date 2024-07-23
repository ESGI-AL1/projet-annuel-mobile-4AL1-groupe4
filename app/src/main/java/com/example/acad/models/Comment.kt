package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Comment(
    val id: Long,
    val program: Long,
    @SerializedName("author_id")
    val authorId: Long,
    val text: String,
    val parent: Long,
    val replies: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)
