package com.example.acad.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Member
import com.example.acad.viewholders.MemberViewHolder

class UserAdapter(private val members: List<Member>,
    private val onItemClicked: (Member) -> Unit) : RecyclerView.Adapter<MemberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.username.text = member.name
        holder.email.text = member.username
        holder.description.text = member.bio
        holder.image.setImageResource(R.drawable.ic_profile)

        holder.button.setOnClickListener {
            Log.d(TAG, "onViewCreated: $member")
            onItemClicked(member)
        }
    }

    override fun getItemCount(): Int = members.size
}