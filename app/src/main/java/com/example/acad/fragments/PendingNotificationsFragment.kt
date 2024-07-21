package com.example.acad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.NotificationAdapter
import com.example.acad.models.Notification

/**
 * A simple [Fragment] subclass.
 * Use the [PendingNotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PendingNotificationsFragment : Fragment() {
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationRecyclerView: RecyclerView

    private val notifications = listOf<Notification>()
    //        Notification(title = "Michael Demande à rejoindre le groupe ....", time = "6 minutes ago", hasActions = true),
//        Notification(title = "User X vous invite à rejoindre le groupe", time = "Jan 4, 2019", hasActions = true)
//    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pending_notifications, container, false)

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        notificationAdapter = NotificationAdapter(notifications)
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = notificationAdapter

        return view
    }

}