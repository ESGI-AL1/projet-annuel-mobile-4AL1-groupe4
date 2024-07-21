package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.GroupAdapter
import com.example.acad.models.Group
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.GroupRepository
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class GroupsActivity : AppCompatActivity() {

    private lateinit var adapter: GroupAdapter
    private lateinit var recyclerView: RecyclerView

    private val groups = emptyList<Group>()

    @Inject
    lateinit var repository: GroupRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_groups)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        val addGroupBtn: MaterialButton = findViewById(R.id.btnAdd)

        backBtn.setOnClickListener {
            finish()
        }

        addGroupBtn.setOnClickListener {
            val intent = Intent(this, AddGroupActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = groupAdapterOnClick(groups)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch { launchRequest() }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch { launchRequest() }
    }

    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            repository.listProgram(token)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    val groups = response.sortedByDescending { it.id }
                    withContext(Dispatchers.Main) {
                        adapter.updateData(groups)
                    }
                }
        }
    }

    private fun groupAdapterOnClick(groups: List<Group>) = GroupAdapter(groups) { group ->
        val intent = Intent(this, GroupDetailActivity::class.java)
        intent.putExtra("groupId", group.id.toString())
        startActivity(intent)
    }
}