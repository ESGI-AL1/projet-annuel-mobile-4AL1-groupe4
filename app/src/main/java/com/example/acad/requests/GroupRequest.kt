package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class GroupRequest(
    @SerializedName("name")
    val title: String,
    val description: String,
    val members: List<Long?>,
//    val photo: String
)
