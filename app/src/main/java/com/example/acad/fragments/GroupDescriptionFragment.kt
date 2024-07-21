package com.example.acad.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.FollowerAdapter
import com.example.acad.adapters.FollowingAdapter
import com.example.acad.data.GroupData
import com.example.acad.models.Group
import com.example.acad.models.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class GroupDescriptionFragment() : Fragment() {

    @Inject
    lateinit var groupData: GroupData

    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingRecyclerView: RecyclerView

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followerRecyclerView: RecyclerView

    private var group: Group? = null
    private lateinit var descriptionTextView: TextView

    // Example data
    private val followings = emptyList<User>()

    private val followers = emptyList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_description, container, false)

//        val group = groupData.group.value
//        descriptionTextView = view.findViewById(R.id.desciptionView)
//        Log.d(ContentValues.TAG, "onCreateView group = : ${group.description}")
//        descriptionTextView.text = group.description

        // Initialize the adapters
        //follower Adapter
//        followerRecyclerView = view.findViewById(R.id.followersRecyclerView)
//        followerAdapter = FollowerAdapter(followers)
//        followerRecyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        followerRecyclerView.adapter = followerAdapter

        //following Adapter
//        followingRecyclerView = view.findViewById(R.id.followingRecyclerView)
//        followingAdapter = FollowingAdapter(followings)
//        followingRecyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        followingRecyclerView.adapter = followingAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        descriptionTextView = view.findViewById(R.id.desciptionView)

        lifecycleScope.launch {
            groupData.group.collect { group ->
                Log.d(TAG, "on lifecyleScope: $group")
                descriptionTextView.text = group.description
            }
        }
    }
}