package com.example.pocketpaladinapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
/*
 Portions of this code were assisted or generated using OpenAI's ChatGPT
 (https://chat.openai.com/) to improve productivity, readability, and functionality.
 Final implementation decisions and code integration were made by the developer.
*/
class AddCategoryActivity : AppCompatActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var categoryNameEditText: EditText
    private lateinit var saveCategoryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category) // Make sure the file name matches

        // Bind views
        backIcon = findViewById(R.id.backIcon)
        categoryNameEditText = findViewById(R.id.categoryNameEditText)
        saveCategoryButton = findViewById(R.id.saveCategoryButton)

        // Back icon click: finish the activity
        backIcon.setOnClickListener {
            finish()
        }

        // Save button click
        saveCategoryButton.setOnClickListener {
            val categoryName = categoryNameEditText.text.toString().trim()

            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("category_name", categoryName)
                setResult(RESULT_OK, resultIntent)
                finish() // Return to CategoryActivity
            }
        }
    }
}