package com.example.pocketpaladinapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var categoryNameEditText: EditText
    private lateinit var saveCategoryButton: Button

    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoryDao

    private var userId: Int = 1 // Replace this with dynamic user ID retrieval as needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        backIcon = findViewById(R.id.backIcon)
        categoryNameEditText = findViewById(R.id.categoryNameEditText)
        saveCategoryButton = findViewById(R.id.saveCategoryButton)

        db = AppDatabase.getDatabase(this)
        categoryDao = db.categoryDao()

        backIcon.setOnClickListener {
            finish()
        }

        saveCategoryButton.setOnClickListener {
            val categoryName = categoryNameEditText.text.toString().trim()

            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val newCategory = Category(
                    userOwnerId = userId,
                    categoryName = categoryName,
                    categoryTotal = 0.0
                )

                categoryDao.insert(newCategory)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddCategoryActivity,
                        "Category added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}