package com.example.acad.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.acad.fragments.GroupDescriptionFragment
import com.example.acad.fragments.GroupMembersFragment
import com.example.acad.models.Group

class GroupDetailsPagerAdapter(
    fragment: FragmentActivity,
    private var group: Group?
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GroupDescriptionFragment()
            1 -> GroupMembersFragment(group)
            else -> throw IllegalStateException("Position inconnue: $position")
        }
    }

    fun updateGroup(groupUp: Group) {
        group = groupUp
    }
}
