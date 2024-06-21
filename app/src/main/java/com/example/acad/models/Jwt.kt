package com.example.acad.models

import com.google.gson.annotations.SerializedName

data class Jwt(
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("access")
    val access: String
)