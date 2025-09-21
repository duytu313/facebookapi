package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(
    private val videos: List<Int>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnPlay: Button = itemView.findViewById(R.id.btnPlayVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoResId = videos[position]
        holder.btnPlay.text = "Video ${position + 1}"
        holder.btnPlay.setOnClickListener {
            onClick(videoResId)
        }
    }

    override fun getItemCount(): Int = videos.size
}
