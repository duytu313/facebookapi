package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var storyAdapter: StoryAdapter

    private val postList = mutableListOf<Post>()
    private val storyList = mutableListOf<Story>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        // RecyclerView cho Story
        storyRecyclerView = view.findViewById(R.id.storyRecyclerView)
        storyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        storyAdapter = StoryAdapter(storyList)
        storyRecyclerView.adapter = storyAdapter

        // RecyclerView cho Post
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(postList)
        recyclerView.adapter = postAdapter

        // load dữ liệu mẫu
        loadDummyData()

        return view
    }

    // Hàm tạo dữ liệu mẫu
    private fun loadDummyData() {
        // Thêm stories
        storyList.clear()
        storyList.addAll(
            listOf(
                Story("Nguyễn Văn A", R.drawable.sample_image),
                Story("Trần Thị B", R.drawable.sample_image),
                Story("Lê Văn C", R.drawable.sample_image)
            )
        )
        storyAdapter.notifyDataSetChanged()

        // Thêm posts
        postList.clear()
        postList.addAll(
            listOf(
                Post(
                    id = "1",
                    userName = "Nguyễn Văn A",
                    content = "Bài viết mẫu 1",
                    imageRes = R.drawable.sample_image,
                    createdAt = "2 giờ trước"
                ),
                Post(
                    id = "2",
                    userName = "Trần Thị B",
                    content = "Video mới up nè",
                    videoRes = null,
                    createdAt = "1 giờ trước"
                ),
                Post(
                    id = "3",
                    userName = "Lê Văn C",
                    content = "Không có ảnh/video, chỉ text thôi",
                    createdAt = "30 phút trước"
                )
            )
        )

        postAdapter.notifyDataSetChanged()
    }

    // Hàm thêm bài viết mới lên đầu
    fun addPostOnTop(post: Post) {
        postList.add(0, post)
        postAdapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }
}
