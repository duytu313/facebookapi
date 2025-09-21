package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    private val posts = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 1️⃣ Story RecyclerView
        storyRecyclerView = view.findViewById(R.id.storyRecyclerView)
        storyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        storyRecyclerView.adapter = StoryAdapter(getSampleStories())

        // 2️⃣ Post RecyclerView
        postsRecyclerView = view.findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(posts)
        postsRecyclerView.adapter = postAdapter

        // 3️⃣ Sample posts
        posts.addAll(getSamplePosts())
        postAdapter.notifyDataSetChanged()

        return view
    }

    // Hàm thêm bài viết mới lên đầu danh sách
    fun addPostOnTop(newPost: Post) {
        posts.add(0, newPost)
        postAdapter.notifyItemInserted(0)
        postsRecyclerView.scrollToPosition(0)
    }

    private fun getSampleStories(): List<Story> {
        return listOf(
            Story("Alice", R.drawable.ic_avatar_placeholder),
            Story("Bob", R.drawable.ic_avatar_placeholder),
            Story("Charlie", R.drawable.ic_avatar_placeholder)
        )
    }

    private fun getSamplePosts(): List<Post> {
        return listOf(
            Post("1", "Alice", "Hello world!", R.drawable.ic_avatar_placeholder, null, "2025-09-21"),
            Post("2", "Bob", "This is my post.", R.drawable.ic_avatar_placeholder, null, "2025-09-21"),
            Post("3", "Charlie", "Check this out!", R.drawable.ic_avatar_placeholder, null, "2025-09-21")
        )
    }
}
