package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter
    private val videoList = mutableListOf<Video>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.videoRecyclerView)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        // Video demo với thumbnail
        videoList.add(Video("dQw4w9WgXcQ", "Video 1", "https://img.youtube.com/vi/dQw4w9WgXcQ/0.jpg"))
        videoList.add(Video("9bZkp7q19f0", "Video 2", "https://img.youtube.com/vi/9bZkp7q19f0/0.jpg"))
        videoList.add(Video("3JZ_D3ELwOQ", "Video 3", "https://img.youtube.com/vi/3JZ_D3ELwOQ/0.jpg"))

        adapter = VideoAdapter(videoList, viewLifecycleOwner)
        adapter.attachRecyclerView(recyclerView)
        recyclerView.adapter = adapter

        // Scroll listener để auto-play video giữa màn hình
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val firstVisible = layoutManager.findFirstVisibleItemPosition()
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val targetPos = (firstVisible + lastVisible) / 2
                    adapter.playVideoAt(targetPos)
                }
            }
        })

        // Play video đầu tiên
        recyclerView.post { adapter.playVideoAt(0) }
    }
}
