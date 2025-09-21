package com.example.facebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val recyclerView: RecyclerView = findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val menuItems = listOf(
            MenuItem(1, R.drawable.ic_account, "Tài khoản"),
            MenuItem(2, R.drawable.ic_privacy, "Quyền riêng tư"),
            MenuItem(3, R.drawable.ic_language, "Ngôn ngữ"),
            MenuItem(4, R.drawable.ic_help, "Trợ giúp & hỗ trợ"),
            MenuItem(5, R.drawable.ic_logout, "Đăng xuất")
        )

        val adapter = MenuAdapter(menuItems, this)
        recyclerView.adapter = adapter
    }
}
