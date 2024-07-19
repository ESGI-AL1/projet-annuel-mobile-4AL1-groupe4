package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    val isOnline: Boolean = false
) {
    constructor(): this(0, "", "")
}
