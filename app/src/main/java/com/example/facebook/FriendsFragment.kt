package com.example.facebook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

class FriendsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendsAdapter: FriendsAdapter
    private val friendsList = mutableListOf<Friend>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        recyclerView = view.findViewById(R.id.friendsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        friendsAdapter = FriendsAdapter(friendsList,
            onAddClick = { friend ->
                Toast.makeText(requireContext(), "Đã gửi lời mời kết bạn tới ${friend.name}", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { friend ->
                val intent = Intent(requireContext(), FriendDetailActivity::class.java)
                intent.putExtra("friend_data", friend)
                startActivity(intent)
            }
        )

        recyclerView.adapter = friendsAdapter

        // Sample data
        friendsList.addAll(
            listOf(
                Friend("1", "Nguyễn Văn A", R.drawable.avatar1, "Online"),
                Friend("2", "Trần Thị B", R.drawable.avatar2, "Offline"),
                Friend("3", "Lê Văn C", R.drawable.avatar3, "Online"),
                Friend("4", "Phạm Thị D", R.drawable.avatar4, "Offline")
            )
        )
        friendsAdapter.notifyDataSetChanged()

        return view
    }

    // Thêm bạn bè mới lên đầu danh sách (nếu cần)
    fun addNewFriend(friend: Friend) {
        friendsList.add(0, friend)
        friendsAdapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }
}
