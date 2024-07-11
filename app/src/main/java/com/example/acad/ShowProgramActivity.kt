package com.example.acad

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.acad.models.Program
import com.example.acad.models.User
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class ShowProgramActivity : AppCompatActivity() {
    lateinit var profileImage: ShapeableImageView
    lateinit var programAuthor: TextView
    lateinit var programDate: TextView
    lateinit var programTitle: TextView
    lateinit var programDescription: TextView
    lateinit var programCode: WebView

    @Inject
    lateinit var repository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_program)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack: MaterialButton = findViewById(R.id.backButton)
        btnBack.setOnClickListener {
            finish()
        }

        // Récupération de l'objet Program à partir de l'intent
        val programId = intent.getParcelableExtra("programId", String::class.java)

        Log.d("TAG Program", "onCreate: $programId")
        // Assurez-vous que l'objet Program n'est pas nul avant de continuer
//        if (program != null) {
        profileImage = findViewById(R.id.profileImage)
        programAuthor = findViewById(R.id.programAuthor)
        programDate = findViewById(R.id.programDate)
        programTitle = findViewById(R.id.programTitle)
        programDescription = findViewById(R.id.programDescription)
        programCode = findViewById(R.id.programCode)

        // Remplir les vues avec les données du programme
//        programAuthor.text = "@Golanginya"
//        programDate.text = "Mis à jour il y a 9 jours"
//        programTitle.text = "Programme pour ..."
//        programDescription.text =
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. Nulla vel accumsan enim. Praesent sit amet lectus blandit neque aliquet laoreet."

        // Charger le code dans WebView
//        programCode.loadData(
//            """
//                <!DOCTYPE html><html><head><style>pre { white-space: pre-wrap; }</style></head><body>
//                <pre>
//                 <code>
//                 class ProgramDetailActivity : AppCompatActivity() {
//
//                    override fun onCreate(savedInstanceState: Bundle?) {
//                        super.onCreate(savedInstanceState)
//                        setContentView(R.layout.activity_program_detail)
//
//                        val btnBack: MaterialButton = findViewById(R.id.backButton)
//                        btnBack.setOnClickListener {
//                            finish()
//                        }
//
//                        // Récupération de l'objet Program à partir de l'intent
//                        val program = intent.getParcelableExtra<Program>("program")
//
//                        // Assurez-vous que l'objet Program n'est pas nul avant de continuer
//                        if (program != null) {
//                            val profileImage: ShapeableImageView = findViewById(R.id.profileImage)
//                            val programAuthor: TextView = findViewById(R.id.programAuthor)
//                            val programDate: TextView = findViewById(R.id.programDate)
//                            val programTitle: TextView = findViewById(R.id.programTitle)
//                            val programDescription: TextView = findViewById(R.id.programDescription)
//                            val programCode: WebView = findViewById(R.id.programCode)
//
//                            // Remplir les vues avec les données du programme
//                            programAuthor.text = "@Golanginya"
//                            programDate.text = program.date
//                            programTitle.text = program.title
//                            programDescription.text = program.description
//
//                            // Charger le code dans WebView
//                            programCode.loadData(program.code, "text/html", "UTF-8")
//                        } else {
//                            // Gérer le cas où l'objet Program est nul
//                            finish() // Fermer l'activité ou afficher un message d'erreur
//                        }
//                    }
//                }
//
//                 </code>
//                 </pre>
//                </body></html>""".trimIndent(),
//            "text/html",
//            "UTF-8"
//        )
//        } else {
//            // Gérer le cas où l'objet Program est nul
//            finish() // Fermer l'activité ou afficher un message d'erreur
//        }
        lifecycleScope.launch { launchRequest(programId = programId!!) }
    }


    private suspend fun launchRequest(programId:String) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            repository.getProgram(it, programId)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
                    Log.d(TAG, "launchRequest: $response")
                    programTitle.text = response.title
                    programAuthor.text = "@${response.author.username}"
                    programDate.text = response.createdAt.parseDate("yyyy-MM-dd")?.format("dd/MM/yyyy")
                    programDescription.text = response.description
                }
        }
    }
}