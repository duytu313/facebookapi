package com.example.facebook

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Lấy userId từ Intent, nếu null thì mặc định "Unknown"
        val userId = intent.getStringExtra("USER_ID") ?: "Unknown"

        // Lấy reference các view từ layout
        val tvUsername: TextView = findViewById(R.id.tvUsername)
        val ivAvatar: ImageView = findViewById(R.id.ivAvatarProfile)

        // Hiển thị thông tin tạm thời
        tvUsername.text = "User ID: $userId"

        // Load avatar tạm bằng Glide (URL ví dụ)
        val avatarUrl = "https://example.com/avatar/$userId.png"
        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.avatar1) // ảnh tạm
            .error(R.drawable.avatar1)       // ảnh khi lỗi
            .into(ivAvatar)
    }
}
