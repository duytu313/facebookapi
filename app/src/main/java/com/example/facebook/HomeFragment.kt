package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Story RecyclerView
        val storyRecyclerView = view.findViewById<RecyclerView>(R.id.storyRecyclerView)
        storyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val stories = listOf(
            Story("Alice", R.drawable.avatar1),
            Story("Bob", R.drawable.avatar2),
            Story("Charlie", R.drawable.avatar3)
        )
        storyRecyclerView.adapter = StoryAdapter(stories)

        // Post RecyclerView
        val postsRecyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val posts = listOf(
            Post("Nguyễn Văn A", "Hôm nay trời đẹp quá!", R.drawable.post1),
            Post("Trần Thị B", "Đi chơi thôi!", R.drawable.post2),
            Post("Lê Văn C", "Làm tí cà phê ☕", R.drawable.post3)
        )
        postsRecyclerView.adapter = PostAdapter(posts)

        return view
    }
}
