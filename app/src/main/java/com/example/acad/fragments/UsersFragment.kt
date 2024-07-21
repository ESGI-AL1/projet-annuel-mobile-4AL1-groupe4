package com.example.acad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.UserAdapter
import com.example.acad.data.UserData
import com.example.acad.data.toMember
import com.example.acad.models.Member
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private lateinit var memberAdapter: UserAdapter
    private lateinit var memberRecyclerView: RecyclerView

    private var members = emptyList<Member>()

    @Inject
    lateinit var userData: UserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        members = userData.list.map { it.toMember() }

        // Initialize the adapter and RecyclerView
        memberRecyclerView = view.findViewById(R.id.usersRecyclerView)
        memberAdapter = UserAdapter(members)
        memberRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        memberRecyclerView.adapter = memberAdapter

        return view
    }

}