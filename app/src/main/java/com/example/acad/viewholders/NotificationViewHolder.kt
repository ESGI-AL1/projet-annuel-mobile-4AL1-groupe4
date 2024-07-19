package com.example.acad.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R

class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val notificationTitle: TextView = itemView.findViewById(R.id.notificationTitle)
    val notificationTime: TextView = itemView.findViewById(R.id.notificationTime)
    val actionButtons: LinearLayout = itemView.findViewById(R.id.actionButtons)
}