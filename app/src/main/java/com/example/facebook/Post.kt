package com.example.facebook

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: String,
    val userName: String,
    val content: String,
    val imageRes: Int? = null,
    val videoRes: Int? = null,
    val createdAt: String,
    var isLiked: Boolean = false    // ✅ thêm trạng thái like
) : Parcelable

