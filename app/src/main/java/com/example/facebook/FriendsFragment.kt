package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val posts = listOf(
            Post("Bạn thân 1", "Check-in phố cổ Hà Nội", R.drawable.post1),
            Post("Bạn thân 2", "Đi biển cùng nhau 🏖️", R.drawable.post2),
            Post("Bạn thân 3", "Cafe cuối tuần ☕", R.drawable.post3)
        )

        recyclerView.adapter = PostAdapter(posts)

        return view
    }
}
