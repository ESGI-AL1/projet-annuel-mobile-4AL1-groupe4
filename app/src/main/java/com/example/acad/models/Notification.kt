package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Notification(
    val id: Int,
    val title: String,
    @SerializedName("recipient_id")
    val recipientId: Int,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("action_type")
    val actionType: String?,
    @SerializedName("program")
    val program: Int?,
    @SerializedName("comment")
    val comment: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("read")
    val read: Boolean,
    val time: String,
    val hasActions: Boolean
)
