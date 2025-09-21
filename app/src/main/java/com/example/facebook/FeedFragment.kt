package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()
    private val currentUserId = "YOUR_USER_ID" // TODO: lấy từ login hoặc SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        postAdapter = PostAdapter(
            posts = postList,
            currentUserId = currentUserId
        ) { post ->
            // Click comment: mở CommentFragment nếu có post.id
            post.id?.let { postId ->
                val fragment = CommentFragment.newInstance(postId)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()

                // Ẩn header khi vào comment
                (activity as? HomeActivity)?.findViewById<View>(R.id.header)?.visibility = View.GONE
            }
        }

        recyclerView.adapter = postAdapter

        loadPostsFromServer()

        return view
    }

    private fun loadPostsFromServer() {
        ApiClient.apiService.getPosts().enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                val body = response.body()
                if (response.isSuccessful && body?.success == true) {
                    val posts = body.posts.map { serverPost ->
                        Post(
                            id = serverPost._id ?: System.currentTimeMillis().toString(),
                            userId = serverPost.userId,
                            userName = serverPost.userName ?: "Người dùng",
                            content = serverPost.content ?: "",
                            imageRes = null,  // chuyển từ imageUrl sang imageRes
                            videoRes = null,  // chuyển từ videoId sang videoRes
                            createdAt = serverPost.createdAt ?: "Vừa xong",
                            isLiked = false
                        )
                    }

                    postList.clear()
                    postList.addAll(posts)
                    postAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Lỗi tải bài viết", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun likePost(post: Post) {
        val postId = post.id ?: return
        ApiClient.apiService.likePost(postId, currentUserId)
            .enqueue(object : Callback<LikeResponse> {
                override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        post.isLiked = !post.isLiked
                        postAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Like thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // Thêm post mới lên đầu danh sách
    fun addPostOnTop(post: Post) {
        postList.add(0, post)
        postAdapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }
}
