package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    val text: String,
    val program: Long,
    @SerializedName("author_id")
    val author: Long
)
