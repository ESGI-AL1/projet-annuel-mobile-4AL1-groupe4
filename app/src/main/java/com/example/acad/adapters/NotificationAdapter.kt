package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Notification
import com.example.acad.viewholders.NotificationViewHolder

class NotificationAdapter(private val notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.notificationTitle.text = notification.title
        holder.notificationTime.text = notification.time
        holder.actionButtons.visibility = if (notification.hasActions) View.VISIBLE else View.GONE
//        holder.username.text = member.name
//        holder.email.text =member.username
//        holder.description.text = member.bio
//        holder.image.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount(): Int = notifications.size
}