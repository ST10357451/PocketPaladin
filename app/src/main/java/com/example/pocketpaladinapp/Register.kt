package com.example.pocketpaladinapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        val usernameField = findViewById<EditText>(R.id.usernameEditText)
        val emailField = findViewById<EditText>(R.id.emailEditText)
        val passwordField = findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordField = findViewById<EditText>(R.id.confirmPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(userName = username, email = email, password = password)

                // Save user
                lifecycleScope.launch {
                    val existing = userDao.getUserByUsername(username)
                    if (existing != null) {
                        runOnUiThread {
                            Toast.makeText(this@Register, "User already exists", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        userDao.insert(user)
                        runOnUiThread {
                            Toast.makeText(this@Register, "User registered!", Toast.LENGTH_SHORT).show()
                            finish() // Go back to login
                        }
                    }
                }
            }
        }
    }
}