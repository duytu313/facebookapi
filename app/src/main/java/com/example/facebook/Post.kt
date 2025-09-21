package com.example.facebook

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: String? = null,           // ID từ server
    val userId: String? = null,       // ID người dùng đăng
    val userName: String? = null,     // Tên người đăng
    val content: String? = null,      // Nội dung bài viết
    val imageRes: Int? = null,        // Resource ID ảnh
    val videoRes: Int? = null,        // Resource ID video (hoặc null)
    val createdAt: String? = null,    // Thời gian tạo
    var isLiked: Boolean = false      // Trạng thái like
) : Parcelable
