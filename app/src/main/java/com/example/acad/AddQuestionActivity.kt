package com.example.acad

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class AddQuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_question)
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

        val categoryInput: AutoCompleteTextView = findViewById(R.id.categoryInput)
        val categories = listOf("Catégorie 1", "Catégorie 2", "Catégorie 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        categoryInput.setAdapter(adapter)

        // Afficher le menu déroulant lors du clic sur le champ de texte
        categoryInput.setOnClickListener {
            categoryInput.showDropDown()
        }

        val addImageButton: MaterialButton = findViewById(R.id.addImageButton)
        addImageButton.setOnClickListener {
            // Gérer le clic du bouton pour ajouter une image
        }

        val draftButton: MaterialButton = findViewById(R.id.draftButton)
        draftButton.setOnClickListener {
            // Gérer le clic du bouton pour mettre en brouillon
        }

        val publishButton: MaterialButton = findViewById(R.id.publishButton)
        publishButton.setOnClickListener {
            // Gérer le clic du bouton pour publier
        }
    }
}