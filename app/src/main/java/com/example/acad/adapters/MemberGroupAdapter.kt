package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Member
import com.example.acad.viewholders.MemberGroupViewHolder

class MemberGroupAdapter(
    private val members: List<Member>
) : RecyclerView.Adapter<MemberGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberGroupViewHolder {
        val binding = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member_group, parent, false)
        return MemberGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberGroupViewHolder, position: Int) {
        val member = members[position]
        holder.username.text = member.name
        holder.email.text = member.username
        holder.description.text = member.bio
        holder.image.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount(): Int = members.size
}