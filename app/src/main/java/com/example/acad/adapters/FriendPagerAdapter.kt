package com.example.acad.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.acad.fragments.FriendsFragment
import com.example.acad.fragments.UsersFragment

class FriendPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UsersFragment()
            1 -> FriendsFragment()
            else -> throw IllegalStateException("Position inconnue: $position")
        }
    }
}