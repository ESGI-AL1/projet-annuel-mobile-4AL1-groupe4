package com.example.acad

import android.content.ContentValues.TAG
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
import com.example.acad.adapters.GroupAdapter
import com.example.acad.data.UserData
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.GroupRepository
import com.example.acad.requests.GroupRequest
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: GroupRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var userData: UserData
    lateinit var adapter: GroupAdapter

    private lateinit var tagsTextEdit: AutoCompleteTextView
    private lateinit var titleTextEdit: EditText
    private lateinit var photoTextEdit: EditText
    private lateinit var descriptionTextEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_group)
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
        val languages = userData.list.map { it.username }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages)
        languagesInput.setAdapter(adapter)

        // Afficher le menu déroulant lors du clic sur le champ de texte
        languagesInput.setOnClickListener {
            languagesInput.showDropDown()
        }

        val createGroupButton: MaterialButton = findViewById(R.id.createProgramButton)

        tagsTextEdit = findViewById(R.id.languagesInput)
        titleTextEdit = findViewById(R.id.titleInput)
//        photoTextEdit = findViewById(R.id.photoInput)
        descriptionTextEdit = findViewById(R.id.descriptionInput)

        createGroupButton.setOnClickListener {
            // Gérer le clic du bouton pour créer le programme
            // Récupérer les données saisies et les envoyer au serveur ou les stocker localement
            val members = listOf(userData.getUser(tagsTextEdit.text.toString())?.id)
            val request = GroupRequest(
                title = titleTextEdit.text.toString(),
                description = descriptionTextEdit.text.toString(),
                members = members,
                photo = photoTextEdit.text.toString()
            )

            lifecycleScope.launch { launchRequest(request) }
        }
    }

    private suspend fun launchRequest(request: GroupRequest) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            repository.createGroup(token, request)
                .catch { exception ->
                    if (exception is HttpException) {
                        val errorBody = exception.response()?.errorBody()?.string()
                        Log.e(TAG, "loginUser: $errorBody", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .onCompletion {
                    repository.listProgram(token).collect {
                        adapter.updateData(it)
                    }
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    repository.listProgram(token)
                    finish()
                }
        }
    }
}