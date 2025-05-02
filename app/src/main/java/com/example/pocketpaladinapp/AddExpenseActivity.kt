package com.example.pocketpaladinapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var btnSelectPhoto: ImageButton
    private lateinit var btnSaveExpense: Button
    private lateinit var btnBack: ImageButton

    private var selectedImageUri: Uri? = null
    private lateinit var db: AppDatabase
    private lateinit var expenseDao: ExpenseDao
    private lateinit var categoryDao: CategoryDao
    private var userId: Int = 1 // Replace with real user ID

    // Image picker
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Bind views
        etDescription = findViewById(R.id.etDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etDate = findViewById(R.id.etDate)
        etStartTime = findViewById(R.id.etStartTime)
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)
        btnBack = findViewById(R.id.btnBack)

        db = AppDatabase.getDatabase(this)
        expenseDao = db.expenseDao()
        categoryDao = db.categoryDao()

        btnBack.setOnClickListener { finish() }

        val categories = arrayOf("Food", "Transport", "Utilities", "Entertainment", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                etDate.setText("$d/${m + 1}/$y")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        etStartTime.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(this, { _, h, m ->
                etStartTime.setText(String.format("%02d:%02d", h, m))
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }

        // Register ActivityResultLauncher for image picking
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { uri ->
                    try {
                        contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }

                    selectedImageUri = uri

                    // Display the image
                    val inputStream: InputStream? = contentResolver.openInputStream(uri)
                    val selectedImage = BitmapFactory.decodeStream(inputStream)
                    btnSelectPhoto.setImageBitmap(selectedImage)
                }
            }
        }

        // Launch image picker when image button is clicked
        btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            pickImageLauncher.launch(intent)
        }

        btnSaveExpense.setOnClickListener {
            val description = etDescription.text.toString()
            val categoryName = spinnerCategory.selectedItem.toString()
            val date = etDate.text.toString()
            val startTime = etStartTime.text.toString()
            val endTime = startTime
            val amount = 100.0 // Placeholder, use EditText if needed
            val photoPath = selectedImageUri?.toString()

            if (description.isEmpty() || date.isEmpty() || startTime.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val category = categoryDao.getCategoryByName(categoryName)
                if (category == null) {
                    Toast.makeText(this@AddExpenseActivity, "Category not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val expense = Expense(
                    userOwnerId = userId,
                    categoryOwnerId = category.categoryId,
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    amount = amount,
                    description = description,
                    photo = photoPath
                )

                expenseDao.insert(expense)
                Toast.makeText(this@AddExpenseActivity, "Expense saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
