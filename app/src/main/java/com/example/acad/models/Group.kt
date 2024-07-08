package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Group(
    val id: Int,
    val name: String,
    @SerializedName("created_at")
    val date: String
)
