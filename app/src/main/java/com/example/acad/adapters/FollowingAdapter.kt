package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.User
import com.example.acad.viewholders.FollowingViewHolder

class FollowingAdapter(private val followings: List<User>) :
    RecyclerView.Adapter<FollowingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_following, parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val following = followings[position]
        holder.followingName.text = following.username
        holder.followingImage.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount() = followings.size
}
