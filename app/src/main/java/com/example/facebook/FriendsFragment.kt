package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FriendsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        recyclerView = view.findViewById(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(posts)
        recyclerView.adapter = postAdapter

        // Sample posts
        posts.addAll(
            listOf(
                Post("1", "Bạn thân 1", "Check-in phố cổ Hà Nội", R.drawable.ic_avatar_placeholder, null, "2025-09-21T08:00:00"),
                Post("2", "Bạn thân 2", "Đi biển cùng nhau 🏖️", R.drawable.ic_avatar_placeholder, null, "2025-09-20T16:30:00"),
                Post("3", "Bạn thân 3", "Cafe cuối tuần ☕", R.drawable.ic_avatar_placeholder, null, "2025-09-19T10:15:00")
            )
        )
        postAdapter.notifyDataSetChanged()

        return view
    }

    // Thêm post mới
    fun addNewPost(userName: String, content: String, imageRes: Int? = null, videoRes: Int? = null) {
        val newPost = Post(
            id = System.currentTimeMillis().toString(),
            userName = userName,
            content = content,
            imageRes = imageRes,
            videoRes = videoRes,
            createdAt = "2025-09-21T12:00:00"
        )
//        postAdapter.addPostOnTop(newPost)
//        recyclerView.scrollToPosition(0)
    }
}
