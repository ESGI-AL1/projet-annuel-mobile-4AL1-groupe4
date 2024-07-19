package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.User
import com.example.acad.viewholders.FollowerViewHolder

class FollowerAdapter(private val followers: List<User>) :
    RecyclerView.Adapter<FollowerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val follower = followers[position]
        holder.followerName.text = follower.username
        holder.followerImage.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount() = followers.size
}
