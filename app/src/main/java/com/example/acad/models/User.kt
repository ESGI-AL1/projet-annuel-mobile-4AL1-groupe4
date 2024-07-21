package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    val isOnline: Boolean = false,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
) {
    constructor() : this(
        id = 0,
        username = "",
        email = "",
        firstName = "",
        lastName = "",
        isOnline = false
    )
}
