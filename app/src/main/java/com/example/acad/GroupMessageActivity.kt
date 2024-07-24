package com.example.acad

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.MessageAdapter
import com.example.acad.models.Group
import com.example.acad.models.Message
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.GroupRepository
import com.example.acad.repositories.MessageRepository
import com.example.acad.requests.MessageRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class GroupMessageActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleTextView: TextView
    private lateinit var messageEditText: TextInputEditText
    private lateinit var backBtn: MaterialButton
    private lateinit var sendButton: MaterialButton

    private var group: Group? = null

    @Inject
    lateinit var repository: MessageRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var groupRepository: GroupRepository

    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_group_message)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleTextView = findViewById(R.id.title)
        backBtn = findViewById(R.id.backButton)
        sendButton = findViewById(R.id.send_button)
        messageEditText = findViewById(R.id.message_input)
        backBtn.setOnClickListener {
            finish()
        }

        val groupId = intent.getParcelableExtra("groupId", String::class.java)

        Log.d(TAG, "onCreate: groupId : $groupId")
        recyclerView = findViewById(R.id.recyclerView)
        adapter = MessageAdapter(messages)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            launchRequest(groupId!!)
        }

        sendButton.setOnClickListener {
            lifecycleScope.launch {
                if (messageEditText.text!!.isNotEmpty()) {
                    dataStoreRepository.readUserId().collect { userId ->
                        val request = MessageRequest(
                            content = messageEditText.text.toString(),
                            groupId = groupId!!.toLong(),
                            sender = userId.toLong(),
                        )
                        lifecycleScope.launch {
                            launchCreateRequest(request)
                        }
                    }
                }
            }
        }
    }

    private suspend fun launchRequest(groupId: String) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            groupRepository.getGroup(token = token, groupId)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
                }
                .onCompletion {
                    launchAllMessage(groupId.toLong())
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    group = response
                    titleTextView.text = group?.name
                }
        }
    }

    private suspend fun launchCreateRequest(request: MessageRequest) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.addMessage(token = it, request)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "Error message: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "Message: $response")
                    launchAllMessage(request.groupId)
                    messageEditText.text = null
                }
        }
    }

    private suspend fun launchAllMessage(groupId: Long) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            repository.allMessage(token = token)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
                }
                .collect { response ->
                    Log.d(TAG, "Messages: $response")
                    val messages = response.sortedByDescending { it.id }
                        .filter { it.groupId == groupId }
                    Log.d(TAG, "launchRequest Messages: $messages")
                    withContext(Dispatchers.Main) {
                        adapter.updateData(messages)
                    }
                }
        }
    }
}