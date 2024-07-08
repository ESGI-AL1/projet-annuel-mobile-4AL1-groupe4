package com.example.acad

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.QuestionAdapter
import com.example.acad.models.Question
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.QuestionRepository
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class MyQuestionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter

    // Création de la liste de questions
    private val questions = emptyList<Question>()

    @Inject
    lateinit var repository: QuestionRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = QuestionAdapter(questions)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch { launchRequest() }
    }


    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.meQuestionList(token = it)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "Questions: $response")
                    withContext(Dispatchers.Main) {
                        adapter.updateData(response)
                    }
                }
        }
    }
}