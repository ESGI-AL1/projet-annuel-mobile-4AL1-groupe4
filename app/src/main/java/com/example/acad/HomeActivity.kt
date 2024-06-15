package com.example.acad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.models.Program
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var topAppBar: MaterialToolbar

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
        val programs = listOf(
            Program(
                1,
                "Programme pour ...",
                "Mis à jour il y a 9 jours",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. ",
                arrayListOf("golang", "linux", "overflow"),
                R.drawable.ic_profile,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                "Golanginya"
            ),
            Program(
                2,
                "Programme pour ...",
                "Mis à jour il y a 9 jours",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. Nulla vel accumsan enim.",
                arrayListOf("golang", "linux", "overflow"),
                R.drawable.ic_profile,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                "Golanginya"
            ),
            Program(
                3,
                "Programme pour ...",
                "Mis à jour il y a 9 jours",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. Nulla vel accumsan enim. Praesent sit amet lectus blandit neque aliquet laoreet.",
                arrayListOf("golang", "linux", "overflow"),
                R.drawable.ic_profile,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                "Golanginya"
            ),
            // Ajoutez plus de programmes ici
        )

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProgramAdapter(programs) { program ->
            val intent = Intent(this, ShowProgramActivity::class.java)
            intent.putExtra("programId", program.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val btnLoadMore = findViewById<Button>(R.id.buttonSeeMore)

        btnLoadMore.setOnClickListener {
            // Code pour charger plus de programmes
            val intent = Intent(this, ProgramsActivity::class.java)
            startActivity(intent)
        }


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
                }

                R.id.menu_mes_programmes -> {
                    // Handle Mes Programmes action
                    val intent = Intent(this, ProgramsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_questions -> {
                    // Handle Questions action
                    val intent = Intent(this, QuestionsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_mes_questions -> {
                    // Handle Mes Questions action
                    val intent = Intent(this, QuestionsActivity::class.java)
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
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}