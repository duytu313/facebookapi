package com.example.facebook

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class MenuItem(
    val id: Int,
    val iconRes: Int,
    val title: String
)

class MenuAdapter(
    private val items: List<MenuItem>,
    private val context: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.ivIcon.setImageResource(item.iconRes)
        holder.tvTitle.text = item.title

        holder.itemView.setOnClickListener {
            when (item.id) {
                1 -> {
                    // Ví dụ mở AccountActivity
                }
                2 -> {
                    // Ví dụ mở PrivacyActivity
                }
                3 -> {
                    // Ví dụ mở LanguageActivity
                }
                4 -> {
                    // Ví dụ mở HelpActivity
                }
                5 -> {
                    // Logout → về LoginActivity
                    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    prefs.edit().clear().apply()

                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
