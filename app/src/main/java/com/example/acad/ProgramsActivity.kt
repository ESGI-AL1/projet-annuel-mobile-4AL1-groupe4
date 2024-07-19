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
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.data.ProgramData
import com.example.acad.models.Program
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class ProgramsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramAdapter

    @Inject
    lateinit var programData: ProgramData

    // Exemple de données
    private val programs = emptyList<Program>()

    @Inject
    lateinit var programRepository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_programs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        recyclerView = findViewById(R.id.recyclerView)
        adapter = programAdapterOnClick(programs)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val btnBack: MaterialButton = findViewById(R.id.backButton)
        btnBack.setOnClickListener {
            // Handle back button click
            finish()
        }

        val btnAdd: MaterialButton = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            // Handle add button click
            val intent = Intent(this, AddProgramActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch { launchRequest() }
    }

    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            programRepository.listProgram(it)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { programs ->
//                    val programs: List<Program> = response
//                        .map { program -> if (program is Program) program else Program() }
                    Log.d(TAG, "launchRequest: $programs")
                    programData.saveList(programs)
                    withContext(Dispatchers.Main) {
                        adapter.updateData(programs)
                    }
                }
        }
    }

    private fun programAdapterOnClick(programs: List<Program>) =
        ProgramAdapter(programs) { program ->
            val intent = Intent(this, ShowProgramActivity::class.java)
            intent.putExtra("programId", program.id)
            startActivity(intent)
        }
}