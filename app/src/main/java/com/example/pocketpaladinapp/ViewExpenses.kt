package com.example.pocketpaladinapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetapp.com.example.pocketpaladinapp.SettingsActivity
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
 Portions of this code were assisted or generated using OpenAI's ChatGPT
 (https://chat.openai.com/) to improve productivity, readability, and functionality.
 Final implementation decisions and code integration were made by the developer.
*/
class ViewExpenses : AppCompatActivity() {

    private lateinit var btnAddNewExpense: ImageButton
    private lateinit var btnBackExpensePage: ImageButton
    private lateinit var btnFilter: Button
    private lateinit var tvDate: TextView
    private lateinit var btnEditBudget: Button
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_expenses_page)

        // Bind views
        btnAddNewExpense = findViewById(R.id.btnAddNewExpense)
        btnBackExpensePage = findViewById(R.id.btnBackExpensePage)
        btnFilter = findViewById(R.id.btnFilter)
        tvDate = findViewById(R.id.tvDate)
        btnEditBudget = findViewById(R.id.btnEditBudget)
        bottomNav = findViewById(R.id.bottomNav)

        // Navigate to AddExpenseActivity
        btnAddNewExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Navigate back to CategoryActivity
        btnBackExpensePage.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
            finish()
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
                Toast.makeText(this, "Filter applied for: $selectedDate", Toast.LENGTH_SHORT).show()
            }, year, month, day)

            datePicker.show()
        }

        // Navigate to SetMonthlyExpendature
        btnEditBudget.setOnClickListener {
            startActivity(Intent(this, SetMonthlyExpendature::class.java))
        }

        // Handle bottom navigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, CategoryActivity::class.java))
                    true
                }

                R.id.nav_expenses -> {
                    // Already in ViewExpenses
                    true
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }

                else -> false
            }
        }

        // Optional: highlight current nav item
        bottomNav.selectedItemId = R.id.nav_expenses
    }
}