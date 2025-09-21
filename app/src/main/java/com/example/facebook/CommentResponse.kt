package com.example.facebook

data class CommentResponse(
    val success: Boolean,
    val message: String,
    val comment: Comment? = null
)
