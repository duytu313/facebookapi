package com.example.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private val currentUserId = "12345"

    fun getCurrentUserId(): String = currentUserId

    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newPost = result.data?.getParcelableExtra<Post>("new_post_object") ?: return@registerForActivityResult
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is HomeFragment) {
                currentFragment.addPostOnTop(newPost)
            }
        }
    }

    private lateinit var header: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        header = findViewById(R.id.header)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val statusInput = findViewById<TextView>(R.id.status_input)
        val photoIcon = findViewById<ImageView>(R.id.photo_icon)
        val ivHeaderAvatar: ImageView = header.findViewById(R.id.ivHeaderAvatar)
        val messengerIcon: ImageView = header.findViewById(R.id.messenger_icon)

        // Load HomeFragment mặc định
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            header.visibility = View.VISIBLE
        }

        // BottomNavigationView
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    header.visibility = View.VISIBLE
                }
                R.id.nav_friends -> {
                    replaceFragment(FriendsFragment())
                    header.visibility = View.GONE
                }
                R.id.nav_watch -> {
                    replaceFragment(VideoFragment())
                    header.visibility = View.GONE
                }
                R.id.nav_notifications -> {
                    replaceFragment(NotificationsFragment())
                    header.visibility = View.GONE
                }
                R.id.nav_marketplace -> {
                    replaceFragment(MenuFragment())
                    header.visibility = View.GONE
                }
            }
            true
        }

        // Tạo bài viết mới
        val openCreatePost = {
            val intent = Intent(this, CreatePostActivity::class.java)
            createPostLauncher.launch(intent)
        }
        statusInput.setOnClickListener { openCreatePost() }
        photoIcon.setOnClickListener { openCreatePost() }

        // Mở ProfileActivity
        ivHeaderAvatar.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", getCurrentUserId())
            startActivity(intent)
        }

        // Mở MessengerActivity
        messengerIcon.setOnClickListener {
            val intent = Intent(this, MessengerActivity::class.java)
            startActivity(intent)
        }
    }

    // Thay fragment tiện ích
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
