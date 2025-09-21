package com.example.facebook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bấm vào icon bánh răng mở SettingsActivity
        view.findViewById<ImageView>(R.id.ivSettings).setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        // Bấm vào item "Bạn bè" mở FriendsActivity
        view.findViewById<LinearLayout>(R.id.llFriends).setOnClickListener {
            startActivity(Intent(requireContext(), FriendsActivity::class.java))
        }

        // Lấy userId từ HomeActivity (phải public hoặc có getter)
        val currentUserId = (activity as? HomeActivity)?.getCurrentUserId() ?: "Unknown"

        // Bấm vào avatar mở ProfileActivity
        view.findViewById<ImageView>(R.id.ivProfileMenu).setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }
    }
}
