package com.example.acad

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.FriendAdapter
import com.example.acad.models.Friend
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class FriendsActivity : AppCompatActivity() {
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendRecyclerView: RecyclerView

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    // Example data
    private val friends = emptyList<Friend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_friends)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: MaterialButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        // Initialize the adapter and RecyclerView
        friendRecyclerView = findViewById(R.id.friendRecyclerView)
        friendAdapter = FriendAdapter(friends)
        friendRecyclerView.layoutManager = LinearLayoutManager(this)
        friendRecyclerView.adapter = friendAdapter


        lifecycleScope.launch { launchRequest() }
    }


    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.listFriend(it)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    withContext(Dispatchers.Main) {
                        friendAdapter.updateData(response)
                    }
                }
        }
    }
}