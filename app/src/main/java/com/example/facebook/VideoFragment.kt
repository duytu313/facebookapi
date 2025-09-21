package com.example.facebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoFragment : Fragment() {

    private val videoList = listOf(
        R.raw.video1,
        R.raw.video2,
        R.raw.video3
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = VideoAdapter(videoList) { videoResId ->
            // Khi bấm vào video -> mở VideoDetailActivity
            val intent = Intent(requireContext(), VideoDetailActivity::class.java)
            intent.putExtra("videoResId", videoResId)
            startActivity(intent)
        }

        return view
    }
}
