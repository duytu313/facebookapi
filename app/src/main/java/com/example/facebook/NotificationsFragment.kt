package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsFragment : Fragment() {

    private val notificationList = listOf(
        NotificationItem("Alice", "đã thích bài viết của bạn", R.drawable.avatar1, "2 giờ trước"),
        NotificationItem("Bob", "đã bình luận: Tuyệt quá!", R.drawable.avatar2, "3 giờ trước"),
        NotificationItem("Charlie", "đã theo dõi bạn", R.drawable.avatar3, "5 giờ trước")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvNotifications)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NotificationsAdapter(notificationList)
        return view
    }
}
