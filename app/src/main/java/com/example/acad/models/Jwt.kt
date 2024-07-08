package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Jwt(
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("access")
    val access: String,
    @SerializedName("user")
    val user: UserJwt,
)

data class UserJwt(
    @SerializedName("user_id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)