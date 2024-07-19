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
 * Use the [AllNotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllNotificationsFragment : Fragment() {
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationRecyclerView: RecyclerView

    private val notifications = listOf <Notification>()
//        Notification(
//            title = "Tony est maintenant votre ami",
//            time = "Just now",
//            hasActions = false
//        ),
//        Notification(
//            title = "Michael Demande à rejoindre le groupe ....",
//            time = "6 minutes ago",
//            hasActions = true
//        ),
//        Notification(title = "Michael a quitté le groupe", time = "Yesterday", hasActions = false)
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_notifications, container, false)

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        notificationAdapter = NotificationAdapter(notifications)
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = notificationAdapter

        return view
    }

}