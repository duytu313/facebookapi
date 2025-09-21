package com.example.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreatePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val statusEditText = findViewById<EditText>(R.id.status_edit_text)
        val postButton = findViewById<Button>(R.id.post_button)

        postButton.setOnClickListener {
            val content = statusEditText.text.toString().trim()
            if (content.isNotEmpty()) {
                val newPost = Post(
                    id = System.currentTimeMillis().toString(),
                    userName = "Người dùng",
                    content = content,
                    imageRes = null,
                    videoRes = null,
                    createdAt = "Vừa xong"
                )

                val resultIntent = Intent()
                resultIntent.putExtra("new_post_object", newPost)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}
