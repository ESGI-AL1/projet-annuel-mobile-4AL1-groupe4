package com.example.acad

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.adapters.GroupAdapter
import com.example.acad.models.Group
import com.google.android.material.button.MaterialButton

class GroupsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private val groups = listOf(
        Group("Nom du groupe", "Date de création"),
        Group("Linuxoid", "il y a 25 min"),
        Group("Linuxoid", "il y a 25 min")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_groups)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: MaterialButton = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        groupAdapter = GroupAdapter(groups)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupAdapter
    }
}