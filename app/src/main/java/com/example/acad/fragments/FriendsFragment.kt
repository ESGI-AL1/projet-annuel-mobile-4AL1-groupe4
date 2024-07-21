package com.example.acad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.FriendAdapter
import com.example.acad.models.Friend
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendRecyclerView: RecyclerView

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    // Example data
    private val friends = emptyList<Friend>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        // Initialize the adapter and RecyclerView
        friendRecyclerView = view.findViewById(R.id.friendRecyclerView)
        friendAdapter = FriendAdapter(friends)
        friendRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        friendRecyclerView.adapter = friendAdapter
        return view
    }

}