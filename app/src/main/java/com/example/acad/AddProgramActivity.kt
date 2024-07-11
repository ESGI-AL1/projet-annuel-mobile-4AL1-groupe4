package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.requests.LoginRequest
import com.example.acad.requests.ProgramRequest
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class AddProgramActivity : AppCompatActivity() {

    @Inject
    lateinit var programRepository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    private lateinit var tagsTextEdit: AutoCompleteTextView
    private lateinit var titleTextEdit: EditText
    private lateinit var descriptionTextEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_program)
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

        val languagesInput: AutoCompleteTextView = findViewById(R.id.languagesInput)
        val languages = listOf("Java", "Kotlin", "Python", "JavaScript", "C#", "C++")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages)
        languagesInput.setAdapter(adapter)

        // Afficher le menu déroulant lors du clic sur le champ de texte
        languagesInput.setOnClickListener {
            languagesInput.showDropDown()
        }

        val createProgramButton: MaterialButton = findViewById(R.id.createProgramButton)

        tagsTextEdit = findViewById(R.id.languagesInput)
        titleTextEdit = findViewById(R.id.titleInput)
        descriptionTextEdit = findViewById(R.id.descriptionInput)

        createProgramButton.setOnClickListener {
            // Gérer le clic du bouton pour créer le programme
            // Récupérer les données saisies et les envoyer au serveur ou les stocker localement
            val request = ProgramRequest(
                title = titleTextEdit.text.toString(),
                description = descriptionTextEdit.text.toString(),
                tags = tagsTextEdit.text.toString().split(",").map { it.trim() }
            )

            lifecycleScope.launch {
                launchRequest(request)
            }
        }
    }

    private suspend fun launchRequest(request: ProgramRequest) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            programRepository.createProgram(it, request)
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