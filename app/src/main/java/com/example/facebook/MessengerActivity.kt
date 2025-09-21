package com.example.facebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessengerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)

        // --- Sample data ---
        val storyList = listOf(
            Story(R.drawable.avatar1, "Cường", true),
            Story(R.drawable.avatar2, "Bùi", true),
            Story(R.drawable.avatar3, "Chinh", false),
            Story(R.drawable.avatar4, "Quỳnh", true),
            Story(R.drawable.avatar5, "Hoàng", false),
            Story(R.drawable.avatar6, "Princess👑❤️", true)
        )

        val chatList = listOf(
            Chat(R.drawable.avatar5, "Hoàng", "Đoạn chat đã được gửi...", "17:21", true),
            Chat(R.drawable.avatar6, "Princess👑❤️", "Đoạn chat đã được gửi...", "17:20", true),
            Chat(R.drawable.avatar1, "Cường", "Hello!", "16:45", false)
        )

        // --- RecyclerViews ---
        val storyRecyclerView = findViewById<RecyclerView>(R.id.storyRecyclerView)
        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)

        storyRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        storyRecyclerView.adapter = StoryAdapter(storyList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = ChatAdapter(chatList)
    }
}
