package com.example.facebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chats: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.chat_avatar)
        val name: TextView = view.findViewById(R.id.chat_user_name)
        val message: TextView = view.findViewById(R.id.chat_message)
        val time: TextView = view.findViewById(R.id.chat_time)
        val online: View = view.findViewById(R.id.chat_onlineIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]
        holder.avatar.setImageResource(chat.avatarResId)
        holder.name.text = chat.name
        holder.message.text = chat.message
        holder.time.text = chat.time
        holder.online.visibility = if (chat.isOnline) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = chats.size
}
