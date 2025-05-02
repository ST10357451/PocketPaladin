package com.example.pocketpaladinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.view.Gravity

class Login : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        val usernameField = findViewById<EditText>(R.id.usernameEditText)
        val passwordField = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val registerLink = findViewById<TextView>(R.id.registerLink)
        val forgotPassLink = findViewById<TextView>(R.id.forgotPasswordLink)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please, Enter Credentials!", Toast.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    val user = userDao.login(username, password)
                    runOnUiThread {
                        if (user != null) {
                            Toast.makeText(this@Login, "Login successful", Toast.LENGTH_SHORT).show()
                            // Redirect to Dashboard
                            val intent = Intent(this@Login, CategoryActivity::class.java)
                            intent.putExtra("userId", user.userId)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Login, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        forgotPassLink.setOnClickListener {
            val toast = Toast.makeText(this, "Sorry, this is not available at the moment", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }

        registerLink.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}