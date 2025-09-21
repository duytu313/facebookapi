package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoryAdapter(private val stories: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.story_avatar)
        val name: TextView = itemView.findViewById(R.id.story_user_name)
        val online: View = itemView.findViewById(R.id.story_onlineIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.avatar.setImageResource(story.imageRes)
        holder.name.text = story.name
        holder.online.visibility = if (story.isViewed) View.GONE else View.VISIBLE
    }

    override fun getItemCount(): Int = stories.size
}
