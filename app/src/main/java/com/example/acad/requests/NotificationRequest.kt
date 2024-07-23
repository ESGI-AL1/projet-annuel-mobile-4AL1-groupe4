package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName("action_type")
    val actionType: String,
    val program: Long,
    val comment: Long,
    val read: Boolean
)
