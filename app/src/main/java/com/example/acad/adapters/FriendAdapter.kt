package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Friend
import com.example.acad.models.User
import com.example.acad.viewholders.FriendViewHolder

class FriendAdapter(private var friends: List<Friend>) : RecyclerView.Adapter<FriendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
//        holder.profileName.text = friend.

        // Example status logic
//        holder.status.text = if (friend.isOnline) "en ligne" else "vu il y a 10 minutes"
//        holder.status.setTextColor(
//            if (friend.isOnline) holder.itemView.context.getColor(R.color.blue) else holder.itemView.context.getColor(
//                R.color.gray
//            )
//        )

        // Load profile image (replace with your image loading logic)
        holder.profileImage.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount() = friends.size
    fun updateData(response: List<Friend>) {
        friends = response
        notifyDataSetChanged()
    }
}