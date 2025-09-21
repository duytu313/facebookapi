package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedFragment : Fragment() {

    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var feedRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        // Stories
        storyRecyclerView = view.findViewById(R.id.storyRecyclerView)
        storyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        storyRecyclerView.adapter = StoryAdapter(getSampleStories())

        // Feed posts
        feedRecyclerView = view.findViewById(R.id.feedRecyclerView)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = PostAdapter(getSamplePosts())

        return view
    }

    private fun getSampleStories(): List<Story> {
        // Dữ liệu mẫu
        return listOf(
            Story("Alice", R.drawable.ic_sample_image),
            Story("Bob", R.drawable.ic_sample_image),
            Story("Charlie", R.drawable.ic_sample_image)
        )
    }

    private fun getSamplePosts(): List<Post> {
        // Dữ liệu mẫu
        return listOf(
            Post("Alice", "Hello world!", R.drawable.ic_sample_image),
            Post("Bob", "This is my post.", R.drawable.ic_sample_image),
            Post("Charlie", "Check this out!", R.drawable.ic_sample_image)
        )
    }
}
