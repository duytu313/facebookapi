package com.example.facebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewFriends)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val friendsList = listOf(
            Friend("1", "Nguyễn Văn A", R.drawable.avatar1, "Online"),
            Friend("2", "Trần Thị B", R.drawable.avatar2, "Offline"),
            Friend("3", "Lê Văn C", R.drawable.avatar3, "Online"),
            Friend("4", "Phạm Thị D", R.drawable.avatar4, "Offline")
        )

        val adapter = FriendsAdapter(
            friendsList.toMutableList(),
            onAddClick = { friend ->
                Toast.makeText(
                    this,
                    "Đã gửi lời mời kết bạn tới ${friend.name}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onItemClick = { friend ->
                val intent = Intent(this, FriendDetailActivity::class.java)
                intent.putExtra("friend_data", friend)
                startActivity(intent)
            }
        )

        recyclerView.adapter = adapter
    }
}
