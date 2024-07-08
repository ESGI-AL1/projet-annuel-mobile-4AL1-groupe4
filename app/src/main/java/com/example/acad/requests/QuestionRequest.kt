package com.example.acad.requests

import com.google.gson.annotations.SerializedName

data class QuestionRequest(
    val title: String,
    val description: String,
    val tags: List<String>,
    @SerializedName("views_count")
    val viewsCount: Int = 0,
    @SerializedName("comments_count")
    val commentsCount: Int = 0,
    @SerializedName("votes_count")
    val votesCount: Int = 0
)