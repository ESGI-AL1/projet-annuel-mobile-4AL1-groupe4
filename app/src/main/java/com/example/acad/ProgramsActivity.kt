package com.example.acad

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.ProgramAdapter
import com.example.acad.models.Program
import com.google.android.material.button.MaterialButton

class ProgramsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_programs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
                "",
                "Golanginya"
            ),
            Program(
                2,
                "Programme pour ...",
                "Mis à jour il y a 9 jours",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. Nulla vel accumsan enim.",
                arrayListOf("golang", "linux", "overflow"),
                R.drawable.ic_profile,
                "",
                "Golanginya"
            ),
            Program(
                3,
                "Programme pour ...",
                "Mis à jour il y a 9 jours",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet enim nec mollis sodales. Nulla vel accumsan enim. Praesent sit amet lectus blandit neque aliquet laoreet.",
                arrayListOf("golang", "linux", "overflow"),
                R.drawable.ic_profile,
                "",
                "Golanginya"
            ),
            // Ajoutez plus de programmes ici
        )

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProgramAdapter(programs) { program ->
            val intent = Intent(this, ShowProgramActivity::class.java)
            intent.putExtra("program", program)
            startActivity(intent)
        }

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
    }
}