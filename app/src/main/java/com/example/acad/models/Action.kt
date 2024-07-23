package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Action(
    val id: Long,
    @SerializedName("author_id")
    val authorId: Long,
    @SerializedName("program_id")
    val programId: Long,
    @SerializedName("comment_id")
    val commentId: Long,
    val action: String,
    @SerializedName("created_at")
    val createdAt: String
)
