package com.example.acad

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
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
import com.example.acad.requests.ProgramRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
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
    private lateinit var fileInput: TextInputEditText
    private val PICK_FILE_REQUEST = 1
    private var selectedFile: File? = null

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
        fileInput = findViewById(R.id.fileInput)

        fileInput.setOnClickListener {
            openFileChooser()
        }

        createProgramButton.setOnClickListener {
            // Gérer le clic du bouton pour créer le programme
            // Récupérer les données saisies et les envoyer au serveur ou les stocker localement
            val request = ProgramRequest(
                title = titleTextEdit.text.toString(),
                description = descriptionTextEdit.text.toString(),
                tags = tagsTextEdit.text.toString().split(",").map { it.trim() },
                file = selectedFile!!
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

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val fileName = getFileName(uri)
                fileInput.setText(fileName)
                selectedFile = getFileFromUri(uri)
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val fileName = getFileName(uri)
        val tempFile = File.createTempFile("temp_", fileName, applicationContext.cacheDir)
        tempFile.outputStream().use { outputStream ->
            contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        Log.d(TAG, "getFileFromUri: $tempFile")
        return tempFile
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "Unknown"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                fileName = it.getString(it.getColumnIndexOrThrow("_display_name"))
            }
        }
        return fileName
    }
}