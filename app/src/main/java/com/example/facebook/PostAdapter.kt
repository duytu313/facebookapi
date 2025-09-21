package com.example.facebook

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostAdapter(
    posts: MutableList<Post>,
    private val currentUserId: String,
    private val onCommentClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val posts: MutableList<Post> = posts

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.post_user)
        val content: TextView = itemView.findViewById(R.id.post_content)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val youtubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.youtube_player_view)
        val btnLike: TextView = itemView.findViewById(R.id.btnLikeText)
        val btnComment: TextView = itemView.findViewById(R.id.btnCommentText)
        val btnShare: TextView = itemView.findViewById(R.id.btnShareText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.userName.text = post.userName
        holder.content.text = post.content

        // Hiển thị ảnh nếu có
        if (post.imageRes != null) {
            holder.postImage.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(post.imageRes)
                .into(holder.postImage)
        } else {
            holder.postImage.visibility = View.GONE
        }

        // Hiển thị video nếu có (giả lập video bằng YouTubePlayerView, videoRes có thể map sang videoId)
        if (post.videoRes != null) {
            holder.youtubePlayerView.visibility = View.VISIBLE
            holder.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // Giả lập: videoRes là resource id, nếu muốn dùng YouTube ID thật thì chuyển đổi
                    val videoId = "dQw4w9WgXcQ" // Demo video
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        } else {
            holder.youtubePlayerView.visibility = View.GONE
        }

        // Cập nhật like
        updateLikeButton(holder, post)
        holder.btnLike.setOnClickListener { toggleLike(post, holder) }

        // Comment
        holder.btnComment.setOnClickListener { onCommentClick(post) }

        // Share
        holder.btnShare.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Share clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = posts.size

    // ====== HÀM PUBLIC ======

    fun updatePosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    fun addPostOnTop(post: Post) {
        posts.add(0, post)
        notifyItemInserted(0)
    }

    // ====== HÀM PRIVATE ======

    private fun updateLikeButton(holder: PostViewHolder, post: Post) {
        holder.btnLike.setTextColor(if (post.isLiked) Color.BLUE else Color.GRAY)
    }

    private fun toggleLike(post: Post, holder: PostViewHolder) {
        val postId = post.id ?: return
        if (post.isLiked) {
            Toast.makeText(holder.itemView.context, "Bạn đã like bài viết này", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClient.apiService.likePost(postId, currentUserId)
            .enqueue(object : Callback<LikeResponse> {
                override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        post.isLiked = true
                        updateLikeButton(holder, post)
                        Toast.makeText(holder.itemView.context, "Đã like", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(holder.itemView.context, "Like thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                    Toast.makeText(holder.itemView.context, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
