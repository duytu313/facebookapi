package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class FeedPost(val username: String, val userAvatar: Int, val content: String)

class FeedAdapter(private val posts: List<FeedPost>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feed_post, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val post = posts[position]
        holder.tvUsername.text = post.username
        holder.ivAvatar.setImageResource(post.userAvatar)
        holder.tvContent.text = post.content
    }

    override fun getItemCount(): Int = posts.size
}
