package com.example.facebook

data class Comment(
    val id: String,
    val postId: String,
    val userName: String,
    val userId: String = "", // nếu cần lưu id user
    val content: String,
    val createdAt: String
)
