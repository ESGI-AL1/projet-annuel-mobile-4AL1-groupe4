package com.example.acad.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.google.android.material.imageview.ShapeableImageView

class FollowingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val followingImage: ShapeableImageView = view.findViewById(R.id.followingImage)
    val followingName: TextView = view.findViewById(R.id.followingName)
}