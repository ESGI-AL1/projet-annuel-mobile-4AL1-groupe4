package com.example.acad.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val message = view.findViewById<TextView>(R.id.message_text)
    val timestamp = view.findViewById<TextView>(R.id.timestamp)
}