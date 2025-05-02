package com.example.pocketpaladinapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.util.*
/*
 Portions of this code were assisted or generated using OpenAI's ChatGPT
 (https://chat.openai.com/) to improve productivity, readability, and functionality.
 Final implementation decisions and code integration were made by the developer.
*/
class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var btnSelectPhoto: ImageButton
    private lateinit var btnSaveExpense: Button
    private lateinit var btnBack: ImageButton

    private val IMAGE_PICK_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_page)

        // Bind views
        etDescription = findViewById(R.id.etDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etDate = findViewById(R.id.etDate)
        etStartTime = findViewById(R.id.etStartTime)
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)
        btnBack = findViewById(R.id.btnBack)

        // Back button action
        btnBack.setOnClickListener {
            finish()
        }

        // Spinner setup
        val categories = arrayOf("Food", "Transport", "Utilities", "Entertainment", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        // Date picker
        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                etDate.setText("$d/${m + 1}/$y")
            }, year, month, day)

            datePicker.show()
        }

        // Time picker
        etStartTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, h, m ->
                etStartTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true)

            timePicker.show()
        }

        // Image picker
        btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        // Save button
        btnSaveExpense.setOnClickListener {
            val description = etDescription.text.toString()
            val category = spinnerCategory.selectedItem.toString()
            val date = etDate.text.toString()
            val time = etStartTime.text.toString()

            Toast.makeText(this, "Expense Saved:\n$description, $category, $date, $time", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            try {
                val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                btnSelectPhoto.setImageBitmap(selectedImage)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
