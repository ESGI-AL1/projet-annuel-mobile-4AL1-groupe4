package com.example.acad.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.google.android.material.button.MaterialButton


class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val username: TextView = view.findViewById(R.id.memberName)
    val email: TextView = view.findViewById(R.id.memberUsername)
    val description: TextView = view.findViewById(R.id.memberBio)
    val image: ImageView = view.findViewById(R.id.memberImage)
    val button: MaterialButton = view.findViewById(R.id.followButton)
}

class MemberGroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val username: TextView = view.findViewById(R.id.memberName)
    val email: TextView = view.findViewById(R.id.memberUsername)
    val description: TextView = view.findViewById(R.id.memberBio)
    val image: ImageView = view.findViewById(R.id.memberImage)
}