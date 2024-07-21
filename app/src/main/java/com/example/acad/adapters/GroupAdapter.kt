package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Group
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.example.acad.viewholders.GroupViewHolder

class GroupAdapter(
    private var groups: List<Group>,
    private val onClick: (Group) -> Unit
) : RecyclerView.Adapter<GroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.groupName.text = group.name
        holder.groupDate.text = group.date.parseDate("yyyy-MM-dd")?.format("dd MMM yyyy")
        // Placeholder for profile image, update with actual image loading
        holder.profileImage.setImageResource(R.drawable.ic_profile)
        holder.iconRight.setOnClickListener {
            // Handle click event to navigate to group details
        }

        holder.itemView.setOnClickListener {
            onClick(group)
        }
    }

    fun updateData(newGroups: List<Group>) {
        groups = newGroups
        notifyDataSetChanged()
    }
}