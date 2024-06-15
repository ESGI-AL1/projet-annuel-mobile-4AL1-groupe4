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
import com.example.acad.adapters.QuestionAdapter
import com.example.acad.models.Question
import com.google.android.material.button.MaterialButton

class QuestionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }

        val questions = listOf(
            Question(
                R.drawable.ic_profile, // Replace with actual drawable
                "Golanginya",
                "il y a 5 minutes",
                "Comment patcher KDE sur FreeBSD ?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Consequat aliquet maecenas ut sit nulla.",
                listOf("golang", "linux", "overflow"),
                125,
                15,
                155
            ),
            // Add more Question objects here
        )

        recyclerView = findViewById(R.id.recyclerView)
        adapter = QuestionAdapter(questions)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}