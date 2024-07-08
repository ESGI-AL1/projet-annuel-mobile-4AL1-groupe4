package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class ResponseRequest(
    val content: String,
    @SerializedName("views_count")
    val votesCount: Int = 0,
)