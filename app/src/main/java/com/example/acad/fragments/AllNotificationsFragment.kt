package com.example.acad.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.adapters.NotificationAdapter
import com.example.acad.models.Friend
import com.example.acad.models.Notification
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class AllNotificationsFragment : Fragment() {
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationRecyclerView: RecyclerView

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    private var notifications = listOf<Notification>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        notificationAdapter = NotificationAdapter(notifications,
            onAcceptClick = { notification ->
                // Handle accept click
                Log.d(TAG, "onViewCreated: is Accept $notification")
                launchAcceptFriend(notification)
            },
            onRejectClick = { notification ->
                // Handle reject click
                Log.d(TAG, "onViewCreated: is Reject $notification")
                launchRejectFriend(notification)
            }
        )
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = notificationAdapter

        launchRequest()

    }

    private fun launchAcceptFriend(friend: Notification) {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.sentFriendRequest(token, friend.senderId, "accept")
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "Error: ${exception.message()}", exception)
                        }
//                _state.value = HttpStatus.ERROR
                    }
                    .collect {
                        Log.d(TAG, "launchRejectFriend: reject ok")
//                        launchRequest()
                        showToast("Ami accepté avec succès")
                    }
            }
        }
    }

    private fun launchRejectFriend(friend: Notification) {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.sentFriendRequest(token, friend.senderId, "reject")
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "Error: ${exception.message()}", exception)
                        }
                    }
                    .collect {
                        Log.d(TAG, "launchRejectFriend: reject ok")
//                        launchRequest()
                        showToast("Ami refusé avec succès")
                    }
            }
        }
    }

    private fun launchRequest() {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.listFriend(token)
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "loginUser: ${exception.message()}", exception)
                        }
//                _state.value = HttpStatus.ERROR
                    }
                    .collect { response ->
                        notifications = response.map { it.toNotification() }
                        notificationAdapter.updateData(notifications)
                    }
            }
        }
    }

    private fun showToast(message: String) {
        // Assurez-vous que le toast est affiché sur le thread principal
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun Friend.toNotification(): Notification = Notification().copy(
    id = id,
    title = "Demande d'ami",
    recipientId = friendId,
    senderId = userId,
    hasActions = status == "sent",
    createdAt = createdAt
)