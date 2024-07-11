package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
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
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.adapters.QuestionAdapter
import com.example.acad.models.Program
import com.example.acad.models.Question
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.GroupRepository
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
class QuestionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter

    // Création de la liste de questions
    private val questions = emptyList<Question>()
//        listOf(
//        Question(
//            R.drawable.ic_profile, // Replace with actual drawable
//            "Golanginya",
//            "il y a 5 minutes",
//            "Comment patcher KDE sur FreeBSD ?",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Consequat aliquet maecenas ut sit nulla.",
//            listOf("golang", "linux", "overflow"),
//            125,
//            15,
//            155
//        ),
//        // Add more Question objects here
//    )

    @Inject
    lateinit var repository: QuestionRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        val addBtn: MaterialButton = findViewById(R.id.addBtn)
        backBtn.setOnClickListener {
            finish()
        }

        // Récupération de l'objet Program à partir de l'intent
        val groupId = intent.getParcelableExtra("groupId", String::class.java)

        Log.d("TAG Group", "onCreate: $groupId")


        addBtn.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            intent.putExtra("groupId", groupId)
            startActivity(intent)
        }


        recyclerView = findViewById(R.id.recyclerView)
        adapter = QuestionAdapter(questions)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch { launchRequest(groupId!!) }
    }

    private suspend fun launchRequest(idGroup: String) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.groupQuestionList(token = it, groupId =  idGroup)
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