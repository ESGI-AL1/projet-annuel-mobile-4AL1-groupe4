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
import com.example.acad.adapters.UserAdapter
import com.example.acad.data.UserData
import com.example.acad.data.toMember
import com.example.acad.models.Member
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import com.example.acad.requests.FriendRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private lateinit var memberAdapter: UserAdapter
    private lateinit var memberRecyclerView: RecyclerView

    private var members = emptyList<Member>()

    @Inject
    lateinit var userData: UserData

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        members = userData.list.map { it.toMember() }

        // Initialize the adapter and RecyclerView
        memberRecyclerView = view.findViewById(R.id.usersRecyclerView)
        memberAdapter = UserAdapter(members) { member ->
            lifecycleScope.launch {
                dataStoreRepository.readUserId().collect { userId ->
                    val request = FriendRequest(
                        friendId = member.id,
                        userId = userId.toLong(),
                        status = "sent"
                    )
                    friendRequest(request)
                }
            }
        }
        memberRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        memberRecyclerView.adapter = memberAdapter

    }

    private suspend fun friendRequest(request: FriendRequest): Unit = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            repository.createFriend(token, request)
                .catch { exception ->
                    if (exception is HttpException) {
                        val errorBody = exception.response()?.errorBody()?.string()
                        Log.e(TAG, "loginUser: $errorBody", exception)
                    }
                }
                .collect { response ->
                    Log.d(TAG, "friendRequest: $response")
                    showToast("Friend Request sent successfully")
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