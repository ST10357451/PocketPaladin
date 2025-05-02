package com.example.pocketpaladinapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var addButton: ImageButton
    private lateinit var filterButton: ImageButton

    private lateinit var navHome: ImageButton
    private lateinit var navExpenses: ImageButton
    private lateinit var navBudgetGoals: ImageButton
    private lateinit var navSettings: ImageButton

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var db: AppDatabase
    private val userId: Int = 1 // Replace this with actual user ID logic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        db = AppDatabase.getDatabase(this)

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        addButton = findViewById(R.id.addButton)
        filterButton = findViewById(R.id.filterButton)

        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navBudgetGoals = findViewById(R.id.navBudgetGoals)
        navSettings = findViewById(R.id.navSettings)

        setupRecyclerView()
        setupNavigation()
        loadCategories()

        addButton.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        filterButton.setOnClickListener {
            // Optional filter action
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun loadCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            val categories = withContext(Dispatchers.IO) {
                db.categoryDao().getAllCategories(userId)
            }
            categoryAdapter.submitList(categories)
        }
    }

    private fun setupNavigation() {
        navHome.setOnClickListener {
            // Navigate to Home
        }
        navExpenses.setOnClickListener {
            // Navigate to Expenses
        }
        navBudgetGoals.setOnClickListener {
            // Navigate to Budget Goals
        }
        navSettings.setOnClickListener {
            // Navigate to settings screen
            val intent = Intent(this, Register::class.java) // chage this to settigs activity
            startActivity(intent)
        }
    }
}