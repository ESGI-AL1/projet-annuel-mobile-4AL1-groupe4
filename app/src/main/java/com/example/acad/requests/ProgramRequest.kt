package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class ProgramRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("tags")
    val tags: List<String>
)
