package com.example.facebook

data class Comment(
    val id: String,
    val userId: String,
    val postId: String,
    val content: String,
    val createdAt: String
)
