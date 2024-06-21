package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("password_confirme")
    val passwordConfirm: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)