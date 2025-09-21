package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FriendsAdapter(
    private val friends: MutableList<Friend>,
    private val onAddClick: (Friend) -> Unit,
    private val onItemClick: (Friend) -> Unit
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.friendAvatar)
        val name: TextView = itemView.findViewById(R.id.friendName)
        val status: TextView = itemView.findViewById(R.id.friendStatus)
        val btnAdd: TextView = itemView.findViewById(R.id.btnAddFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.name.text = friend.name
        holder.status.text = friend.status
        if (friend.avatarRes != null) {
            Glide.with(holder.itemView.context)
                .load(friend.avatarRes)
                .circleCrop()
                .into(holder.avatar)
        } else {
            holder.avatar.setImageResource(R.drawable.avatar1) // default
        }

        holder.btnAdd.setOnClickListener {
            onAddClick(friend)
        }

        holder.itemView.setOnClickListener {
            onItemClick(friend)
        }
    }

    override fun getItemCount(): Int = friends.size
}
