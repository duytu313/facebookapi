package com.example.facebook

data class Post(
    val username: String,
    val content: String,
    val imageResId: Int?, // ảnh bài viết, nullable nếu không có
    var likeCount: Int = 0,
    var commentCount: Int = 0,
    var shareCount: Int = 0,
    var isLiked: Boolean = false
)
