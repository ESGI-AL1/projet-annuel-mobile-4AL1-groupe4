package com.example.acad.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.User
import com.google.android.material.imageview.ShapeableImageView

class FollowerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val followerImage: ShapeableImageView = view.findViewById(R.id.followerImage)
    val followerName: TextView = view.findViewById(R.id.followerName)
}