package com.example.acad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.data.ProgramData
import com.example.acad.data.UserData
import com.example.acad.models.Program
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var topAppBar: MaterialToolbar

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
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        topAppBar = findViewById(R.id.topAppBar)

        setSupportActionBar(topAppBar)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Exemple de données
        val programs = emptyList<Program>()

        recyclerView = findViewById(R.id.recyclerView)
        adapter = programAdapterOnClick(programs)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val btnLoadMore = findViewById<Button>(R.id.buttonSeeMore)

        btnLoadMore.setOnClickListener {
            // Code pour charger plus de programmes
            val intent = Intent(this, ProgramsActivity::class.java)
            startActivity(intent)
        }

        val headerView: View = navigationView.getHeaderView(0)
        val profileName: TextView = headerView.findViewById(R.id.profileName)
        val profileUsername: TextView = headerView.findViewById(R.id.profileUsername)
        profileName.text = userData.userState.value.username
        profileUsername.text = userData.userState.value.email

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_rechercher -> {
                    // Handle Rechercher action
                }

                R.id.menu_programmes -> {
                    // Handle Programmes action
                    val intent = Intent(this, ProgramsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_pipeline -> {
                    // Handle Pipeline action
                    val intent = Intent(this, NotificationsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_mes_programmes -> {
                    // Handle Mes Programmes action
                    val intent = Intent(this, ProgramsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_questions -> {
                    // Handle Questions action
                    val intent = Intent(this, AllQuestionActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_mes_questions -> {
                    // Handle Mes Questions action
                    val intent = Intent(this, MyQuestionActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_mes_reponses -> {
                    // Handle Mes Réponses action
                }

                R.id.menu_mes_groupes -> {
                    // Handle Mes Groupes action
                    val intent = Intent(this, GroupsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_mes_amis -> {
                    // Handle Mes Amis action
                    val intent = Intent(this, FriendsActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        lifecycleScope.launch { launchRequest() }
    }

    private suspend fun launchRequest() = withContext(Dispatchers.IO) {
        dataStoreRepository.readAccessToken().collect {
            programRepository.publicListProgram(it)
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
            intent.putExtra("programId", program.id.toString())
            startActivity(intent)
        }
}