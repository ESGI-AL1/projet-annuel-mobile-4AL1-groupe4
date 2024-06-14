package com.example.acad.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R

class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val profileImage: ImageView = view.findViewById(R.id.profileImage)
    val programTitle: TextView = view.findViewById(R.id.programTitle)
    val programDate: TextView = view.findViewById(R.id.programDate)
    val programDescription: TextView = view.findViewById(R.id.programDescription)
    val tagsLayout: LinearLayout = view.findViewById(R.id.tagsLayout)
}