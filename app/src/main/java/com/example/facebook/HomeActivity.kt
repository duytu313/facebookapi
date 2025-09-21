package com.example.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_CREATE_POST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Load mặc định FeedFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FeedFragment())
                .commit()
        }

        // Navigation bottom
        bottomNav.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> FeedFragment()
                R.id.nav_friends -> FriendsFragment()
                R.id.nav_watch -> VideoFragment()
                R.id.nav_notifications -> NotificationsFragment()
                R.id.nav_marketplace -> MenuFragment()
                else -> FeedFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
            true
        }

        // Mở CreatePostActivity khi click
        val statusInput = findViewById<TextView>(R.id.status_input)
        val photoIcon = findViewById<ImageView>(R.id.photo_icon)

        val openCreatePost = {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CREATE_POST)
        }

        statusInput.setOnClickListener { openCreatePost() }
        photoIcon.setOnClickListener { openCreatePost() }
    }

    // Nhận dữ liệu từ CreatePostActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE_POST && resultCode == Activity.RESULT_OK) {
            val newPost = data?.getParcelableExtra<Post>("new_post_object") ?: return
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is FeedFragment) {
                currentFragment.addPostOnTop(newPost)
            }
        }
    }
}
