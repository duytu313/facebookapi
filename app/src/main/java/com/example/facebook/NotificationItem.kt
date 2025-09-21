package com.example.facebook

data class NotificationItem(
    val username: String,
    val message: String,
    val avatarRes: Int,      // ảnh đại diện
    val time: String         // ví dụ: "2 giờ trước"
)
