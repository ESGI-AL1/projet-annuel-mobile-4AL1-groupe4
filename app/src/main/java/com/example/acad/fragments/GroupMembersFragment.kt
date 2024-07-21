package com.example.acad.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.MembersAdapter
import com.example.acad.data.GroupData
import com.example.acad.data.UserData
import com.example.acad.data.toMember
import com.example.acad.models.Group
import com.example.acad.models.Member
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [GroupMembersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GroupMembersFragment(private val group: Group?) : Fragment() {

    private lateinit var memberAdapter: MembersAdapter
    private lateinit var memberRecyclerView: RecyclerView

    @Inject
    lateinit var groupData: GroupData

    @Inject
    lateinit var userData: UserData

    // Example data
    private var members = emptyList<Member>()
//        listOf(
//        Member(
//            id = 1,
//            "Tony Moreno",
//            "@Moreno21",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
//            R.drawable.ic_profile
//        ),
//        Member(
//            id = 2,
//            "Nicholas Urfe",
//            "@nick87",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
//            R.drawable.ic_profile
//        ),
//        Member(
//            id = 3,
//            "Bob Anders",
//            "@Bobbb",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean commodo ligula eget.",
//            R.drawable.ic_profile
//        )
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            groupData.group.collect { group ->
                val users = userData.list
                members = group.members
                    .map { users.find { user -> user.id.toLong() == it.toLong() } }
                    .map {
                        Log.d(TAG, "onCreate: $it")
                        it!!.toMember()
                    }
            }
        }

//        members = group.members.map { userData.getUser(it) }.map { it.toMember() }
        memberRecyclerView = view.findViewById(R.id.membersRecyclerView)
        memberAdapter = MembersAdapter(members)
        memberRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        memberRecyclerView.adapter = memberAdapter

    }
}