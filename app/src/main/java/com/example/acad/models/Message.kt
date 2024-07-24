package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Message(
    val id: Long,
    @SerializedName("group_id")
    val groupId: Long,
    val content: String,
    val timestamp: String,
    val sender: Long,
    val receiver: Long
)
