package com.example.acad.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.acad.fragments.GroupDescriptionFragment
import com.example.acad.fragments.GroupMembersFragment
import com.example.acad.models.Group
import com.example.acad.models.Member

class GroupDetailsPagerAdapter(
    fragment: FragmentActivity,
    private val group: Group?,
    private val members: List<Member>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GroupDescriptionFragment(group)
            1 -> GroupMembersFragment(members)
            else -> throw IllegalStateException("Position inconnue: $position")
        }
    }
}
