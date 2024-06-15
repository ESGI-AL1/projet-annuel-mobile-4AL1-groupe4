package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Group
import com.example.acad.viewholders.GroupViewHolder

class GroupAdapter(private val groups: List<Group>) : RecyclerView.Adapter<GroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.groupName.text = group.name
        holder.groupDate.text = group.date
        // Placeholder for profile image, update with actual image loading
        holder.profileImage.setImageResource(R.drawable.ic_profile)
        holder.iconRight.setOnClickListener {
            // Handle click event to navigate to group details
        }
    }
}