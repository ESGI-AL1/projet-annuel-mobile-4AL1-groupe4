package com.example.acad.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val profileImage: ShapeableImageView = itemView.findViewById(R.id.profileImage)
    val profileName: TextView = itemView.findViewById(R.id.profileName)
    val status: TextView = itemView.findViewById(R.id.status)
    val action: LinearLayout = itemView.findViewById(R.id.actionButtons)
    val validButton: MaterialButton = itemView.findViewById(R.id.acceptButton)
    val rejectButton: MaterialButton = itemView.findViewById(R.id.rejectButton)
}