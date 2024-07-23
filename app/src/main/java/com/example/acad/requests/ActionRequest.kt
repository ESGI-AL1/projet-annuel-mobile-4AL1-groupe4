package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class ActionRequest(
    val action: String,
    @SerializedName("program_id")
    val programId: Long = 0,
    @SerializedName("comment_id")
    val commentId: Long? = null,
)
