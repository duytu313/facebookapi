package com.example.facebook

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        val videoView = findViewById<VideoView>(R.id.videoView)

        val videoResId = intent.getIntExtra("videoResId", -1)
        if (videoResId != -1) {
            val uri = Uri.parse("android.resource://${packageName}/$videoResId")
            videoView.setVideoURI(uri)
            videoView.setMediaController(MediaController(this))
            videoView.start()
        }
    }
}
