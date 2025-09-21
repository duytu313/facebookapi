package com.example.facebook

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class ReelsAdapter(private val videoList: List<Int>) :
    RecyclerView.Adapter<ReelsAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reel_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoResId = videoList[position]
        val uri = Uri.parse("android.resource://${holder.itemView.context.packageName}/$videoResId")
        holder.videoView.setVideoURI(uri)
        holder.videoView.setOnPreparedListener {
            it.isLooping = true
            holder.videoView.start()
        }

        // Click mở VideoDetailActivity
        holder.videoView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, VideoDetailActivity::class.java)
            intent.putExtra("video_res_id", videoResId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = videoList.size
}
