package com.example.acad.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.google.android.material.imageview.ShapeableImageView

class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val profileImage: ShapeableImageView = view.findViewById(R.id.profileImage)
    val authorName: TextView = view.findViewById(R.id.authorName)
    val questionTime: TextView = view.findViewById(R.id.questionTime)
    val questionTitle: TextView = view.findViewById(R.id.questionTitle)
    val questionDescription: TextView = view.findViewById(R.id.questionDescription)
    val tagsLayout: LinearLayout = view.findViewById(R.id.tagsLayout)
    val viewsCount: TextView = view.findViewById(R.id.viewsCount)
    val commentsCount: TextView = view.findViewById(R.id.commentsCount)
    val votesCount: TextView = view.findViewById(R.id.votesCount)
}