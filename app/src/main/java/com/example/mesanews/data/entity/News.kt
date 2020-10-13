package com.example.mesanews.data.entity

import com.google.gson.annotations.SerializedName

data class AllResults(
    @SerializedName("data")
    val list: List<News>
)

data class News (
    val title: String,
    val description: String,
//    val content: String,
//    val author: String,
//    val published_at: String,
    val highlight: Boolean,
    val url: String,
    val image_url: String
)