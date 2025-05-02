package com.example.pocketpaladinapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BudgetGoalActivity : AppCompatActivity() {

    private lateinit var etMonthInput: EditText
    private lateinit var etMinLimitInput: EditText
    private lateinit var etMaxLimitInput: EditText
    private lateinit var btnSaveLimit: Button
    private lateinit var btnBack: ImageButton
    private lateinit var spendingHistoryRecyclerView: RecyclerView
    private lateinit var adapter: BudgetGoalAdapter

    private lateinit var db: AppDatabase
    private var userId: Int = 1 // Replace with actual user ID logic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_goal)

        // Initialize UI elements
        etMonthInput = findViewById(R.id.etMonthInput)
        etMinLimitInput = findViewById(R.id.etMinLimitInput)
        etMaxLimitInput = findViewById(R.id.etMaxLimitInput)
        btnSaveLimit = findViewById(R.id.btnSaveLimit)
        btnBack = findViewById(R.id.btnBack)
        spendingHistoryRecyclerView = findViewById(R.id.spendingHistoryRecyclerView)

        // Initialize RecyclerView
        adapter = BudgetGoalAdapter()
        spendingHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        spendingHistoryRecyclerView.adapter = adapter

        // Get database instance
        db = AppDatabase.getDatabase(this)

        // Load existing goals from DB
        loadPreviousGoals()

        // Save new budget goal
        btnSaveLimit.setOnClickListener {
            val month = etMonthInput.text.toString().trim()
            val minText = etMinLimitInput.text.toString().trim()
            val maxText = etMaxLimitInput.text.toString().trim()

            if (month.isEmpty() || minText.isEmpty() || maxText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val min = minText.toDoubleOrNull()
            val max = maxText.toDoubleOrNull()

            if (min == null || max == null || min > max) {
                Toast.makeText(this, "Invalid limits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = BudgetGoal(
                userOwnerId = userId,
                month = month,
                minGoal = min,
                maxGoal = max
            )

            lifecycleScope.launch {
                db.budgetGoalDao().insert(goal)
                loadPreviousGoals()
                Toast.makeText(this@BudgetGoalActivity, "Limit Saved", Toast.LENGTH_SHORT).show()
                clearInputs()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadPreviousGoals() {
        lifecycleScope.launch {
            val allGoals = withContext(Dispatchers.IO) {
                db.budgetGoalDao().getAllGoals(userId)
            }
            adapter.submitList(allGoals)
        }
    }

    private fun clearInputs() {
        etMonthInput.text.clear()
        etMinLimitInput.text.clear()
        etMaxLimitInput.text.clear()
    }
}
