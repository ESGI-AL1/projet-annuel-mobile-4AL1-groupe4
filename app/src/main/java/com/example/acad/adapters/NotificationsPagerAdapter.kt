package com.example.acad.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.acad.NotificationsActivity
import com.example.acad.fragments.AllNotificationsFragment
import com.example.acad.fragments.PendingNotificationsFragment

class NotificationsPagerAdapter(fragment: NotificationsActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllNotificationsFragment()
            1 -> PendingNotificationsFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
