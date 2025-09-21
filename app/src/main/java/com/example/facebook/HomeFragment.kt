package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModel: PostViewModel
    private val currentUserId = "YOUR_USER_ID"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // --- Story RecyclerView ---
        storyRecyclerView = view.findViewById(R.id.storyRecyclerView)
        storyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        storyRecyclerView.adapter = StoryAdapter(getSampleStories())

        // --- Post RecyclerView ---
        postsRecyclerView = view.findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)

        // --- Lấy ViewModel dùng chung với Activity ---
        viewModel = ViewModelProvider(requireActivity())[PostViewModel::class.java]

        postAdapter = PostAdapter(mutableListOf(), currentUserId) { post ->
            val fragment = CommentFragment.newInstance(post.id ?: "")
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

            (activity as? HomeActivity)?.findViewById<View>(R.id.header)?.visibility = View.GONE
        }
        postsRecyclerView.adapter = postAdapter

        // --- Observe LiveData từ ViewModel ---
        viewModel.posts.observe(viewLifecycleOwner) { postList ->
            postAdapter.updatePosts(postList)
        }

        // --- Tải bài viết từ server khi mở fragment ---
        viewModel.loadPostsFromServer()

        return view
    }

    // --- Thêm bài viết mới lên đầu feed ---
    fun addPostOnTop(newPost: Post) {
        viewModel.addPost(newPost)        // cập nhật ViewModel & server
        postAdapter.addPostOnTop(newPost) // cập nhật adapter
        postsRecyclerView.scrollToPosition(0)
    }

    // --- Sample Stories ---
    private fun getSampleStories(): List<Story> {
        return listOf(
            Story(R.drawable.avatar1, "Cường", true),
            Story(R.drawable.avatar2, "Bùi", true),
            Story(R.drawable.avatar3, "Chinh", false),
            Story(R.drawable.avatar4, "Quỳnh", true),
            Story(R.drawable.avatar5, "Hoàng", false),
            Story(R.drawable.avatar6, "Princess👑❤️", true)
        )
    }
}
