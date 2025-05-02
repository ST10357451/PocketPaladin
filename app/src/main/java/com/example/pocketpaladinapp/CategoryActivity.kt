package com.example.pocketpaladinapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.com.example.pocketpaladinapp.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pocketpaladinapp.ViewExpenses

/*
 Portions of this code were assisted or generated using OpenAI's ChatGPT
 (https://chat.openai.com/) to improve productivity, readability, and functionality.
 Final implementation decisions and code integration were made by the developer.
*/

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Initialize RecyclerView
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        categoryRecyclerView.adapter = categoryAdapter

        // Add button logic
        val addButton: ImageButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE)
        }

        // 🔽 Filter button logic
        val filterButton: ImageButton = findViewById(R.id.filterButton)
        filterButton.setOnClickListener {
            categoryList.sortBy { it.lowercase() }
            categoryAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Categories sorted alphabetically", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true
                R.id.nav_expenses -> {
                    startActivity(Intent(this, ViewExpenses::class.java))
                    true
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    // This handles result from AddCategoryActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            val newCategory = data?.getStringExtra("category_name")
            if (!newCategory.isNullOrEmpty()) {
                categoryList.add(newCategory)
                categoryAdapter.notifyItemInserted(categoryList.size - 1)
            } else {
                Toast.makeText(this, "No category added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ADD_CATEGORY_REQUEST_CODE = 100
    }
}