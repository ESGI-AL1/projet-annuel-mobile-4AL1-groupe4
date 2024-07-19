package com.example.acad

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.acad.data.ProgramData
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class ShowProgramActivity : AppCompatActivity() {
    lateinit var profileImage: ShapeableImageView
    private lateinit var programAuthor: TextView
    private lateinit var programDate: TextView
    private lateinit var programTitle: TextView
    private lateinit var programDescription: TextView
    private lateinit var programCode: WebView

    @Inject
    lateinit var programData: ProgramData

    @Inject
    lateinit var repository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

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

        // Assurez-vous que l'objet Program n'est pas nul avant de continuer
//        if (program != null) {
        profileImage = findViewById(R.id.profileImage)
        programAuthor = findViewById(R.id.programAuthor)
        programDate = findViewById(R.id.programDate)
        programTitle = findViewById(R.id.programTitle)
        programDescription = findViewById(R.id.programDescription)
        programCode = findViewById(R.id.programCode)

        Log.d(TAG, "id: $programId")
        val program = programId?.let { id -> programData.find(id.toLong()) }
        Log.d(TAG, "program: $program")
        if (program != null) {
            programTitle.text = program.title
            programAuthor.text = "@Unknown"

            programDate.text = program.createdAt.parseDate("yyyy-MM-dd")
                ?.format("dd/MM/yyyy")
            programDescription.text = program.description

            // Configurer les paramètres de la WebView
            val webSettings: WebSettings = programCode.settings
            webSettings.javaScriptEnabled = true
            programCode.webViewClient = WebViewClient()

            // Chargement du contenu du fichier dans WebView
            programCode.loadUrl(program.file)
        }
    }

}