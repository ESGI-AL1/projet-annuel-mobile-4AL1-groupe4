package com.example.acad.data

import com.google.gson.annotations.SerializedName

data class JwtDecodeData (
    @SerializedName("user_id")
    val userId: String,
    val exp: String,
    @SerializedName("token_type")
    val tokenType: String
)