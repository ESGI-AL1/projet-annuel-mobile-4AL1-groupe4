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
import com.example.acad.adapters.FriendAdapter
import com.example.acad.data.UserData
import com.example.acad.models.Friend
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendRecyclerView: RecyclerView

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var userData: UserData

    // Example data
    private var friends = emptyList<Friend>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and RecyclerView
        friendRecyclerView = view.findViewById(R.id.friendRecyclerView)
        friendAdapter = FriendAdapter(friends, userData.list,
            onAcceptClick = { friend ->
                // Handle accept click
                Log.d(TAG, "onViewCreated: is Accept $friend")
                launchAcceptFriend(friend)
            },
            onRejectClick = { friend ->
                // Handle reject click
                Log.d(TAG, "onViewCreated: is Reject $friend")
                launchRejectFriend(friend)
            }
        )
        friendRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        friendRecyclerView.adapter = friendAdapter

        launchRequest()
    }

    override fun onResume() {
        super.onResume()
        launchRequest()
    }

    private fun launchAcceptFriend(friend: Friend) {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.sentFriendRequest(token, friend.friendId, "accept")
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "loginUser: ${exception.message()}", exception)
                        }
//                _state.value = HttpStatus.ERROR
                    }
                    .collect {
                        Log.d(TAG, "launchRejectFriend: reject ok")
//                        launchRequest()
                    }
            }
        }
    }

    private fun launchRejectFriend(friend: Friend) {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.sentFriendRequest(token, friend.friendId, "reject")
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "loginUser: ${exception.message()}", exception)
                        }
                    }
                    .collect {
                        Log.d(TAG, "launchRejectFriend: reject ok")
//                        launchRequest()
                    }
            }
        }
    }

    private fun launchRequest() {
        lifecycleScope.launch {
            dataStoreRepository.readAccessToken().collect { token ->
                repository.listFriends(token)
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "loginUser: ${exception.message()}", exception)
                        }
//                _state.value = HttpStatus.ERROR
                    }
                    .collect { friendsResponse ->
                        friends = friendsResponse.filter { it.status != "sent" }.sortedByDescending { it.id }
                        Log.d(TAG, "loginUser: $friendsResponse")
                        friendAdapter.updateData(friends)
                    }
            }
        }
    }
}