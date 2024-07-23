package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Comment
import com.example.acad.models.User
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.example.acad.viewholders.CommentViewHolder

class CommentAdapter(private var comments: List<Comment>, private val users: List<User>) :
    RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        val user = users.find { it.id == comment.authorId }!!
        holder.text.text = comment.text
        holder.author.text = user.username
        holder.date.text = comment.createdAt.parseDate("yyyy-MM-dd")?.format("dd/MM/yyyy")
//        holder.followerImage.setImageResource(R.drawable.ic_profile)
    }

    fun updateData(commentList: List<Comment>) {
        comments = commentList
        notifyDataSetChanged()
    }
}