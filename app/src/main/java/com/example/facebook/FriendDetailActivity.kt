package com.example.facebook

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FriendDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_detail)

        val avatar: ImageView = findViewById(R.id.friendDetailAvatar)
        val name: TextView = findViewById(R.id.friendDetailName)
        val status: TextView = findViewById(R.id.friendDetailStatus)

        val friend = intent.getParcelableExtra<Friend>("friend_data")
        friend?.let {
            name.text = it.name
            status.text = it.status

            // Load avatar từ resource ID
            val avatarRes = it.avatarRes ?: R.drawable.avatar3
            Glide.with(this)
                .load(avatarRes)
                .circleCrop()
                .into(avatar)
        }
    }
}
