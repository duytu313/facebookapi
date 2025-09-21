package com.example.facebook
data class AuthResponse(
    val success: Boolean,
    val message: String,
    val userId: String?,
    val token: String?
)
