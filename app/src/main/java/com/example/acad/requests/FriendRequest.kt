package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class FriendRequest(
    @SerializedName("user")
    var userId: Long,
    @SerializedName("friend")
    var friendId: Long,
    @SerializedName("status")
    val status: String
) {
    constructor() : this(0, 0, "")
}
