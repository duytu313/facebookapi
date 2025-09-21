package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoAdapter(
    private val videos: List<Video>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerView: YouTubePlayerView = view.findViewById(R.id.itemYoutubePlayerView)
        val title: TextView = view.findViewById(R.id.videoTitle)
        val thumbnail: ImageView = view.findViewById(R.id.videoThumbnail)
        var youTubePlayer: YouTubePlayer? = null
    }

    private var currentPlayingPosition = -1
    private var recyclerView: RecyclerView? = null

    fun attachRecyclerView(rv: RecyclerView) {
        recyclerView = rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.title.text = video.title

        // Load thumbnail
        Glide.with(holder.thumbnail.context)
            .load(video.thumbnail)
            .into(holder.thumbnail)
        holder.thumbnail.visibility = View.VISIBLE

        // Thêm observer lifecycle
        lifecycleOwner.lifecycle.addObserver(holder.playerView)

        // Thêm listener
        holder.playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                holder.youTubePlayer = youTubePlayer
                youTubePlayer.cueVideo(video.id, 0f)

                // Nếu đang play video này, play luôn
                if (position == currentPlayingPosition) {
                    holder.thumbnail.visibility = View.GONE
                    youTubePlayer.play()
                }
            }
        })
    }

    override fun getItemCount(): Int = videos.size

    fun playVideoAt(position: Int) {
        if (position == currentPlayingPosition) return

        // Pause video cũ
        recyclerView?.findViewHolderForAdapterPosition(currentPlayingPosition)?.let {
            val oldHolder = it as VideoViewHolder
            oldHolder.youTubePlayer?.pause()
            oldHolder.thumbnail.visibility = View.VISIBLE
        }

        currentPlayingPosition = position

        // Play video mới
        recyclerView?.findViewHolderForAdapterPosition(position)?.let {
            val newHolder = it as VideoViewHolder
            newHolder.thumbnail.visibility = View.GONE
            newHolder.youTubePlayer?.play()
        }
    }
}
