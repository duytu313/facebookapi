package com.example.facebook

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val ivPost: ImageView = itemView.findViewById(R.id.ivPost)
        val ivLike: ImageView = itemView.findViewById(R.id.ivLike)
        val ivComment: ImageView = itemView.findViewById(R.id.ivComment)
        val ivShare: ImageView = itemView.findViewById(R.id.ivShare)
        val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)
        val tvCommentCount: TextView = itemView.findViewById(R.id.tvCommentCount)
        val tvShareCount: TextView = itemView.findViewById(R.id.tvShareCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.tvUsername.text = post.username
        holder.tvContent.text = post.content

        // Nếu có hình ảnh, set resource
        post.imageResId?.let { holder.ivPost.setImageResource(it) }

        holder.tvLikeCount.text = post.likeCount.toString()
        holder.tvCommentCount.text = post.commentCount.toString()
        holder.tvShareCount.text = post.shareCount.toString()

        // Set màu Like lúc load
        holder.tvLikeCount.setTextColor(if (post.isLiked) Color.parseColor("#1877F2") else Color.BLACK)

        // Click Like (toggle màu xanh/đen)
        holder.ivLike.setOnClickListener {
            post.isLiked = !post.isLiked
            if (post.isLiked) {
                post.likeCount++
                holder.tvLikeCount.setTextColor(Color.parseColor("#1877F2"))
            } else {
                post.likeCount--
                holder.tvLikeCount.setTextColor(Color.BLACK)
            }
            holder.tvLikeCount.text = post.likeCount.toString()
        }

        // Click Comment
        holder.ivComment.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Comment clicked!", Toast.LENGTH_SHORT).show()
        }

        // Click Share
        holder.ivShare.setOnClickListener {
            post.shareCount++
            holder.tvShareCount.text = post.shareCount.toString()
            Toast.makeText(holder.itemView.context, "Shared!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = posts.size
}
