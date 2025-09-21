package com.example.facebook

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import android.widget.VideoView

class PostAdapter(private val posts: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.post_user)
        val content: TextView = itemView.findViewById(R.id.post_content)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val postVideo: VideoView = itemView.findViewById(R.id.post_video)
        val likeIcon: ImageView = itemView.findViewById(R.id.btnLikeIcon)
        val likeText: TextView = itemView.findViewById(R.id.btnLikeText)
        val commentIcon: ImageView = itemView.findViewById(R.id.btnCommentIcon)
        val commentText: TextView = itemView.findViewById(R.id.btnCommentText)
        val shareIcon: ImageView = itemView.findViewById(R.id.btnShareIcon)
        val shareText: TextView = itemView.findViewById(R.id.btnShareText)
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
            holder.postImage.setImageResource(post.imageRes)
        } else {
            holder.postImage.visibility = View.GONE
        }

        // Hiển thị video nếu có
        if (post.videoRes != null) {
            holder.postVideo.visibility = View.VISIBLE
            val uri = Uri.parse("android.resource://${holder.postVideo.context.packageName}/${post.videoRes}")
            holder.postVideo.setVideoURI(uri)
            holder.postVideo.seekTo(1) // show preview
        } else {
            holder.postVideo.visibility = View.GONE
        }

        // Cập nhật trạng thái Like
        if (post.isLiked) {
            holder.likeIcon.setColorFilter(Color.BLUE)
            holder.likeText.setTextColor(Color.BLUE)
        } else {
            holder.likeIcon.setColorFilter(Color.GRAY)
            holder.likeText.setTextColor(Color.GRAY)
        }

        // Click Like
        holder.likeIcon.setOnClickListener {
            post.isLiked = !post.isLiked
            notifyItemChanged(position)
        }
        holder.likeText.setOnClickListener {
            post.isLiked = !post.isLiked
            notifyItemChanged(position)
        }

        // Click Comment
        val context: Context = holder.itemView.context
        holder.commentIcon.setOnClickListener {
            Toast.makeText(context, "Comment clicked", Toast.LENGTH_SHORT).show()
        }
        holder.commentText.setOnClickListener {
            Toast.makeText(context, "Comment clicked", Toast.LENGTH_SHORT).show()
        }

        // Click Share
        holder.shareIcon.setOnClickListener {
            Toast.makeText(context, "Share clicked", Toast.LENGTH_SHORT).show()
        }
        holder.shareText.setOnClickListener {
            Toast.makeText(context, "Share clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = posts.size

    // Thêm bài viết mới lên đầu
    fun addPost(post: Post) {
        posts.add(0, post)
        notifyItemInserted(0)
    }
}
