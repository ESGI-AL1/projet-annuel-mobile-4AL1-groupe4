package com.example.acad.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text : TextView = view.findViewById(R.id.commentText)
    val date : TextView = view.findViewById(R.id.commentDate)
    val author : TextView = view.findViewById(R.id.commentAuthor)
}