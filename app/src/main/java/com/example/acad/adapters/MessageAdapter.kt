package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Message
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.example.acad.viewholders.MessageViewHolder

class MessageAdapter(private var messages: List<Message>) :
    RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.message.text = message.content
        holder.timestamp.text = message.timestamp.parseDate("yyyy-MM-dd'T'HH:mm")
            ?.format("HH:mm")
    }

    fun updateData(response: List<Message>) {
        messages = response
        notifyDataSetChanged()
    }
}