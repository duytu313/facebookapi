package com.example.facebook

data class LoginRequest(
    val usernameOrEmail: String, // phải đúng tên này
    val password: String
)
