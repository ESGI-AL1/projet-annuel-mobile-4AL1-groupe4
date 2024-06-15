package com.example.acad.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.google.android.material.imageview.ShapeableImageView

class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val profileImage: ShapeableImageView = view.findViewById(R.id.profileImage)
    val groupName: TextView = view.findViewById(R.id.groupName)
    val groupDate: TextView = view.findViewById(R.id.groupDate)
    val iconRight: ImageView = view.findViewById(R.id.iconRight)
}