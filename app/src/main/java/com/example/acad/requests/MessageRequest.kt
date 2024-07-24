package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class MessageRequest (
    val content: String,
    @SerializedName("group_id")
    val groupId: Long,
    val sender: Long,
)