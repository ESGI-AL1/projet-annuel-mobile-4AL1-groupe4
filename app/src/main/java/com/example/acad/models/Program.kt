package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class ShowProgram(
    val id: Int,
    val file: String,
    @SerializedName("created_at")
    val createdAt: String,
    val isVisible: Boolean,
    val title: String,
    val date: String,
    val description: String,
    val tags: ArrayList<String>?,
    val profileImage: Int,
    @SerializedName("file")
    val code: String,
    val author: Long,
    @SerializedName("input_type")
    val inputType: String,
    @SerializedName("output_type")
    val outputType: String,
)

data class Program(
    val id: Long,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String,
    val author: Long,
    val file: String,
    @SerializedName("input_type")
    val inputType: String,
    @SerializedName("output_type")
    val outputType: String,
    val isVisible: Boolean,
    val tags: List<String>?,
) {
    constructor() : this(
        id = 0,
        title = "",
        description = "",
        createdAt = "",
        author = 0,
        file = "",
        inputType = "",
        outputType = "",
        isVisible = false,
        tags = null
    )

}