package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Notification(
    val id: Long,
    val title: String,
    @SerializedName("recipient_id")
    val recipientId: Long,
    @SerializedName("sender_id")
    val senderId: Long,
    @SerializedName("action_type")
    val actionType: String?,
    @SerializedName("program")
    val program: Long?,
    @SerializedName("comment")
    val comment: Long?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("read")
    val read: Boolean,
    val time: String,
    val hasActions: Boolean
) {
    constructor(): this(0, "", 0, 0, null, null, null, "", false, "", false)
}
