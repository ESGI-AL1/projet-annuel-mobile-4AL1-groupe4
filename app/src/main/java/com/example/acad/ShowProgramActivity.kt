package com.example.acad

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.CommentAdapter
import com.example.acad.data.ProgramData
import com.example.acad.data.UserData
import com.example.acad.models.Comment
import com.example.acad.repositories.CommentRepository
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.requests.ActionRequest
import com.example.acad.requests.CommentRequest
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommentAdapter
    private lateinit var fab: FloatingActionButton

    lateinit var profileImage: ShapeableImageView
    private lateinit var programAuthor: TextView
    private lateinit var programDate: TextView
    private lateinit var programTitle: TextView
    private lateinit var programDescription: TextView
    private lateinit var programCode: WebView
    private lateinit var likeButton: MaterialButton

    private var comments = emptyList<Comment>()
    private val URL = "file:///android_asset/index.html";

    @Inject
    lateinit var programData: ProgramData

    @Inject
    lateinit var userData: UserData

    @Inject
    lateinit var repository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var commentRepository: CommentRepository

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

        recyclerView = findViewById(R.id.commentRecyclerView)
        adapter = CommentAdapter(comments, userData.list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

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
        likeButton = findViewById(R.id.likeButton)
        fab = findViewById(R.id.fab)



        Log.d(TAG, "id: $programId")
        val program = programId?.let { id -> programData.find(id.toLong()) }
        val user = program?.author.let { id -> userData.getUser(id!!.toLong()) }
        Log.d(TAG, "program: $program")
        if (program != null) {
            programTitle.text = program.title
            programAuthor.text = if (user != null) "@${user.username}" else "@Unknown"

            programDate.text = program.createdAt.parseDate("yyyy-MM-dd")
                ?.format("dd/MM/yyyy")
            programDescription.text = program.description

            // Configurer les paramètres de la WebView
//            programCode.webViewClient = WebViewClient()
//            val webSettings: WebSettings = programCode.settings
//            webSettings.javaScriptEnabled = true
//            webSettings.domStorageEnabled = true
//            webSettings.allowFileAccess = true
//            webSettings.builtInZoomControls = true // Allow zooming
//            webSettings.displayZoomControls = false
//            webSettings.javaScriptCanOpenWindowsAutomatically = true
//            programCode.addJavascriptInterface(WebAppInterface(this), "Android")
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                webSettings.allowFileAccessFromFileURLs = true
//                webSettings.allowUniversalAccessFromFileURLs = true
//            }
            setupWebView()

            // Chargement du contenu du fichier dans WebView program.file
            programCode.loadUrl(URL + "?fileUrl=${program.file}")
        }
        likeButton.setOnClickListener {
            lifecycleScope.launch {
                launchLike(ActionRequest("like", programId = program!!.id))
            }
        }

        fab.setOnClickListener {
            showAddCommentDialog(program!!.id)
        }

        lifecycleScope.launch {
            launchComment()
        }
    }

    private fun showAddCommentDialog(programId: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ajouter un commentaire")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        builder.setView(input)

        builder.setPositiveButton("Ajouter") { dialog, which ->
            val comment = input.text.toString()
            // Gérer l'ajout du commentaire
            lifecycleScope.launch {
                dataStoreRepository.readUserId().collect { userId ->
                    val request = CommentRequest(
                        text = comment,
                        program = programId,
                        author = userId.toLong()
                    )
                    launchStoreComment(request)
                }
            }
        }
        builder.setNegativeButton("Annuler") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private suspend fun launchStoreComment(request: CommentRequest) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            commentRepository.createComment(token, request)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
//                    val programs: List<Program> = response
//                        .map { program -> if (program is Program) program else Program() }
                    Log.d(TAG, "launchRequest: $response")
                    launchComment()
                }
        }
    }

    private suspend fun launchComment() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            commentRepository.listComment(token)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
//                    val programs: List<Program> = response
//                        .map { program -> if (program is Program) program else Program() }
                    Log.d(TAG, "launchRequest: $response")
                    withContext(Dispatchers.Main) {
                        adapter.updateData(response)
                    }
                }
        }
    }

    private suspend fun launchLike(request: ActionRequest) = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            commentRepository.addLike(token, request)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { response ->
//                    val programs: List<Program> = response
//                        .map { program -> if (program is Program) program else Program() }
                    Log.d(TAG, "launchRequest: $response")
                    showToast("Like added successfully")
                }
        }
    }

    private fun showToast(message: String) {
        // Assurez-vous que le toast est affiché sur le thread principal
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(this@ShowProgramActivity, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupWebView() {
        programCode.webViewClient = WebViewClient()
        val webSettings: WebSettings = programCode.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.allowFileAccess = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.allowFileAccessFromFileURLs = true
            webSettings.allowUniversalAccessFromFileURLs = true
        }
    }

    /** Instantiate the interface and set the context.  */
    inner class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page.  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }
}