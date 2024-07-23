package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Friend
import com.example.acad.models.User
import com.example.acad.viewholders.FriendViewHolder

class FriendAdapter(
    private var friends: List<Friend>,
    private val users: List<User>,
    private val onAcceptClick: ((Friend) -> Unit)? = null,
    private val onRejectClick: ((Friend) -> Unit)? = null
) : RecyclerView.Adapter<FriendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        val user = users.find { it.id == friend.friendId }
        holder.profileName.text = user?.username

        // Example status logic
        when (friend.status) {
            "sent" -> {
                holder.action.visibility = View.VISIBLE
                holder.status.visibility = View.GONE
            }

            else -> {
                holder.action.visibility = View.GONE
                holder.status.text = friend.status
            }
        }

        holder.validButton.setOnClickListener {
            onAcceptClick?.invoke(friend)
        }

        holder.rejectButton.setOnClickListener {
            onRejectClick?.invoke(friend)
        }

        // Load profile image (replace with your image loading logic)
        holder.profileImage.setImageResource(R.drawable.ic_profile)
    }

    override fun getItemCount() = friends.size

    fun updateData(response: List<Friend>) {
        friends = response
        notifyDataSetChanged()
    }
}