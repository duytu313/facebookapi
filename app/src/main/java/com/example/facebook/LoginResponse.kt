package com.example.facebook

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?
)