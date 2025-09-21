package com.example.facebook

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePostActivity : AppCompatActivity() {

    private val currentUserId = "YOUR_USER_ID" // TODO: Lấy từ login hoặc SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val statusEditText = findViewById<EditText>(R.id.status_edit_text)
        val postButton = findViewById<Button>(R.id.post_button)

        postButton.setOnClickListener {
            val content = statusEditText.text.toString().trim()
            if (content.isNotEmpty()) {
                sendPostToServer(content)
            } else {
                Toast.makeText(this, "Nhập nội dung bài viết", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendPostToServer(content: String) {
        val userIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), currentUserId)
        val contentBody = RequestBody.create("text/plain".toMediaTypeOrNull(), content)

        ApiClient.apiService.createPost(userIdBody, contentBody, null)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    val body = response.body()
                    if (response.isSuccessful && body?.success == true && body.post != null) {
                        Toast.makeText(this@CreatePostActivity, "Đăng bài thành công", Toast.LENGTH_SHORT).show()

                        val serverPost = body.post!!

                        // Map server post -> local Post mới với imageRes/videoRes null
                        val newPost = Post(
                            id = serverPost._id ?: System.currentTimeMillis().toString(),
                            userId = currentUserId,
                            userName = "Bạn",
                            content = serverPost.content ?: "",
                            imageRes = null,   // Server chưa gửi image resource
                            videoRes = null,   // Server chưa gửi video resource
                            createdAt = serverPost.createdAt ?: "Vừa xong",
                            isLiked = false
                        )

                        // Trả kết quả về HomeFragment/FriendsFragment
                        val resultIntent = intent
                        resultIntent.putExtra("new_post_object", newPost)
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this@CreatePostActivity, body?.message ?: "Đăng bài thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Toast.makeText(this@CreatePostActivity, "Lỗi kết nối server", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
