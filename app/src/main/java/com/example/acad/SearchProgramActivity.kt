package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.data.ProgramData
import com.example.acad.data.UserData
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
class SearchProgramActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramAdapter
    private lateinit var searchInput: SearchView
    private lateinit var searchBtn: MaterialButton
    private lateinit var backButton: MaterialButton

    val programs = emptyList<Program>()

    @Inject
    lateinit var userData: UserData

    @Inject
    lateinit var programData: ProgramData

    @Inject
    lateinit var programRepository: ProgramRepository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_program)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = programAdapterOnClick(programs)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchBtn = findViewById(R.id.search_button)
        searchInput = findViewById(R.id.search_input)

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }


        // Ajouter un écouteur de recherche à la barre de recherche
        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")
                query?.let {
                    // Fonction pour filtrer les recettes en fonction de la recherche
                    adapter.searchProgram(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Fonction pour filtrer les recettes en fonction de la recherche
                    Log.d(TAG, "onQueryTextChange program :$programs")
                    adapter.searchProgram(it)
                }
                return true
            }
        })

        lifecycleScope.launch { launchRequest() }
    }

    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect { token ->
            programRepository.publicListProgram(token)
                .catch { exception ->
                    if (exception is HttpException) {
                        Log.e(TAG, "loginUser: ${exception.message()}", exception)
                    }
//                _state.value = HttpStatus.ERROR
                }
                .collect { programs ->
                    val sortPrograms = programs.sortedByDescending { it.id }
                    Log.d(TAG, "launchRequest: $programs")
                    programData.saveList(sortPrograms)
                    withContext(Dispatchers.Main) {
                        adapter.updateData(sortPrograms)
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch { launchRequest() }
    }

    private fun programAdapterOnClick(programs: List<Program>) =
        ProgramAdapter(programs) { program ->
            val intent = Intent(this, ShowProgramActivity::class.java)
            intent.putExtra("programId", program.id.toString())
            startActivity(intent)
        }
}