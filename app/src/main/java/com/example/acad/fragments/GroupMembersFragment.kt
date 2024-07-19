package com.example.acad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.MembersAdapter
import com.example.acad.models.Member
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [GroupMembersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GroupMembersFragment(private val membersAdapter: List<Member>) : Fragment() {

    private lateinit var memberAdapter: MembersAdapter
    private lateinit var memberRecyclerView: RecyclerView


    // Example data
    private var members = listOf(
        Member(
            id = 1,
            "Tony Moreno",
            "@Moreno21",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
            R.drawable.ic_profile
        ),
        Member(
            id = 2,
            "Nicholas Urfe",
            "@nick87",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
            R.drawable.ic_profile
        ),
        Member(
            id = 3,
            "Bob Anders",
            "@Bobbb",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
            R.drawable.ic_profile
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_members, container, false)


//        members = group.members.map { userData.getUser(it) }.map { it.toMember() }
        memberRecyclerView = view.findViewById(R.id.membersRecyclerView)
        memberAdapter = MembersAdapter(members)
        memberRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        memberRecyclerView.adapter = memberAdapter

        return view
    }

}