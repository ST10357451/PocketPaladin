package com.example.pocketpaladinapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ViewExpenses : AppCompatActivity() {

    private lateinit var btnAddNewExpense: ImageButton
    private lateinit var btnBackExpensePage: ImageButton
    private lateinit var btnFilter: Button
    private lateinit var tvDate: TextView
    private lateinit var btnEditBudget: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_expenses_page)

        // Bind views
        btnAddNewExpense = findViewById(R.id.btnAddNewExpense)
        btnBackExpensePage = findViewById(R.id.btnBackExpensePage)
        btnFilter = findViewById(R.id.btnFilter)
        tvDate = findViewById(R.id.tvDate) // Make sure to set this ID on your date TextView
        btnEditBudget = findViewById(R.id.btnEditBudget)

        // Navigate to AddExpenseActivity
        btnAddNewExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // Navigate back to view categories
        btnBackExpensePage.setOnClickListener {
            val intent = Intent(this, CategoryActivity ::class.java)
            startActivity(intent)
            finish() // Optional: prevent user from coming back here with back button
        }

        // Filter by date
        btnFilter.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val selectedDate = String.format("%02d/%02d/%04d", d, m + 1, y)
                tvDate.text = selectedDate
                // TODO: filter expenses based on selectedDate
                Toast.makeText(this, "Filter applied for: $selectedDate", Toast.LENGTH_SHORT).show()
            }, year, month, day)

            datePicker.show()
        }

        // Navigate to EditMonthlyExpenditureActivity
        btnEditBudget.setOnClickListener {
            val intent = Intent(this, BudgetGoalActivity ::class.java)
            startActivity(intent)
        }
    }
}