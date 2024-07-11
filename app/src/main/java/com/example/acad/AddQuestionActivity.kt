package com.example.acad

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.QuestionRepository
import com.example.acad.requests.QuestionRequest
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class AddQuestionActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: QuestionRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    private lateinit var tagsTextEdit: AutoCompleteTextView
    private lateinit var titleTextEdit: EditText
    private lateinit var descriptionTextEdit: EditText

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnBack: MaterialButton = findViewById(R.id.backButton)
        btnBack.setOnClickListener {
            // Gérer le clic du bouton retour
            finish()
        }

        val categoryInput: AutoCompleteTextView = findViewById(R.id.categoryInput)
        val categories = listOf("Catégorie 1", "Catégorie 2", "Catégorie 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        categoryInput.setAdapter(adapter)

        // Afficher le menu déroulant lors du clic sur le champ de texte
        categoryInput.setOnClickListener {
            categoryInput.showDropDown()
        }


        tagsTextEdit = findViewById(R.id.categoryInput)
        titleTextEdit = findViewById(R.id.titleInput)
        descriptionTextEdit = findViewById(R.id.questionInput)

        val addImageButton: MaterialButton = findViewById(R.id.addImageButton)
        addImageButton.setOnClickListener {
            // Gérer le clic du bouton pour ajouter une image
        }

        val draftButton: MaterialButton = findViewById(R.id.draftButton)
        draftButton.setOnClickListener {
            // Gérer le clic du bouton pour mettre en brouillon
        }

        val publishButton: MaterialButton = findViewById(R.id.publishButton)


        // Récupération de l'objet Program à partir de l'intent
        val groupId = intent.getParcelableExtra("groupId", String::class.java)

        Log.d("TAG Group", "onCreate: $groupId")

        publishButton.setOnClickListener {
            // Gérer le clic du bouton pour publier
            val request = QuestionRequest(
                title = titleTextEdit.text.toString(),
                description = descriptionTextEdit.text.toString(),
                tags = tagsTextEdit.text.toString().split(",").map { it.trim() }
            )

            lifecycleScope.launch {
                launchRequest(groupId!!, request)
            }
        }
    }

    private suspend fun launchRequest(groupId: String, request: QuestionRequest) =
        withContext(Dispatchers.IO) {
            dataStoreRepository.readAccessToken().collect {
                repository.createQuestion(it, groupId, request)
                    .catch { exception ->
                        if (exception is HttpException) {
                            val errorBody = exception.response()?.errorBody()?.string()
                            Log.e(TAG, "loginUser: $errorBody", exception)
                        }
//                _state.value = HttpStatus.ERROR
                    }
                    .collect { response ->
                        Log.d(TAG, "launchRequest: $response")
                        finish()
                    }
            }
        }
}