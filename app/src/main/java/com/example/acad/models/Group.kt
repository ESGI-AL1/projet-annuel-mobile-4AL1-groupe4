package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Group(
    val id: Int,
    val name: String,
    @SerializedName("created_at")
    val date: String,
    val description: String,
    @SerializedName("author_id")
    val author: String,
    val members: List<Double>
) {
    constructor() : this(0, "", "", "", "", listOf())
}

