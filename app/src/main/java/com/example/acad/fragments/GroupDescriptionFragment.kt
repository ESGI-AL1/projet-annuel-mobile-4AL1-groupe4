package com.example.acad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.FollowerAdapter
import com.example.acad.adapters.FollowingAdapter
import com.example.acad.models.Group
import com.example.acad.models.User

class GroupDescriptionFragment(private val group: Group?) : Fragment() {

    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingRecyclerView: RecyclerView

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followerRecyclerView: RecyclerView

    // Example data
    private val followings = listOf(
        User(id = 1, username = "Douglas", email = "douglas@example.com"),
        User(id = 2, username = "Max", email = "max@example.com"),
    )

    private val followers = listOf(
        User(id = 1, username = "Hanna", email = "hanna@example.com"),
        User(id = 2, username = "Mary", email = "mary@example.com"),
        User(id = 3, username = "Andrew", email = "andrew@example.com")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_description, container, false)

        val descriptionTextView: TextView = view.findViewById(R.id.desciptionView)
        descriptionTextView.text = group?.description

        // Initialize the adapters
        //follower Adapter
        followerRecyclerView = view.findViewById(R.id.followersRecyclerView)
        followerAdapter = FollowerAdapter(followers)
        followerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        followerRecyclerView.adapter = followerAdapter

        //following Adapter
        followingRecyclerView = view.findViewById(R.id.followingRecyclerView)
        followingAdapter = FollowingAdapter(followings)
        followingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        followingRecyclerView.adapter = followingAdapter

        return view
    }

}