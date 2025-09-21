package com.example.facebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel() {

    // LiveData danh sách bài viết
    private val _posts = MutableLiveData<MutableList<Post>>(mutableListOf())
    val posts: LiveData<MutableList<Post>> = _posts

    // ===== Thêm bài viết mới lên đầu =====
    fun addPost(post: Post) {
        // Thêm local trước
        val updatedList = _posts.value ?: mutableListOf()
        updatedList.add(0, post)
        _posts.value = updatedList

        // Gửi lên server
        val userIdBody: RequestBody = post.userId
            ?.toRequestBody("text/plain".toMediaTypeOrNull())
            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
        val contentBody: RequestBody = post.content
            ?.toRequestBody("text/plain".toMediaTypeOrNull())
            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.createPost(userIdBody, contentBody, null)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.post?.let { serverPost ->
                            // Map serverPost sang Post local
                            val updatedPost = Post(
                                id = serverPost._id ?: post.id,
                                userId = serverPost.userId ?: post.userId,
                                userName = post.userName,             // server không trả, giữ local
                                content = serverPost.content ?: post.content,
                                imageRes = null,                       // server trả URL, chưa map sang resource
                                videoRes = null,                       // server không trả
                                createdAt = serverPost.createdAt ?: post.createdAt,
                                isLiked = false                        // mặc định false
                            )
                            val currentList = _posts.value ?: mutableListOf()
                            currentList[0] = updatedPost
                            _posts.value = currentList
                        }
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    // Xử lý lỗi kết nối nếu muốn (ví dụ: Log hoặc Toast)
                }
            })
    }

    // ===== Cập nhật toàn bộ danh sách bài viết =====
    fun updatePosts(newPosts: List<Post>) {
        _posts.value = newPosts.toMutableList()
    }

    // ===== Load bài viết từ server =====
    fun loadPostsFromServer() {
        ApiClient.apiService.getPosts().enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val postsFromServer = response.body()?.posts ?: emptyList()
                    val localPosts = postsFromServer.map { serverPost ->
                        Post(
                            id = serverPost._id,
                            userId = serverPost.userId,
                            userName = null,       // server không trả, có thể thay bằng tên mặc định
                            content = serverPost.content,
                            imageRes = null,       // server trả URL, để null hoặc map sau
                            videoRes = null,       // server không trả
                            createdAt = serverPost.createdAt,
                            isLiked = false        // mặc định false
                        )
                    }
                    _posts.value = localPosts.toMutableList()
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                // Xử lý lỗi kết nối nếu muốn
            }
        })
    }
}
