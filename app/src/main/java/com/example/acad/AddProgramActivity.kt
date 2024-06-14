package com.example.acad

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class AddProgramActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_program)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack: MaterialButton = findViewById(R.id.backButton)
        btnBack.setOnClickListener {
            // Gérer le clic du bouton retour
            finish()
        }

        val languagesInput: AutoCompleteTextView = findViewById(R.id.languagesInput)
        val languages = listOf("Java", "Kotlin", "Python", "JavaScript", "C#", "C++")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages)
        languagesInput.setAdapter(adapter)

        // Afficher le menu déroulant lors du clic sur le champ de texte
        languagesInput.setOnClickListener {
            languagesInput.showDropDown()
        }

        val createProgramButton: MaterialButton = findViewById(R.id.createProgramButton)
        createProgramButton.setOnClickListener {
            // Gérer le clic du bouton pour créer le programme
            // Récupérer les données saisies et les envoyer au serveur ou les stocker localement
        }
    }
}