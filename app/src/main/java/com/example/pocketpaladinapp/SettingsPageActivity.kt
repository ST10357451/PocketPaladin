package com.example.pocketpaladinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SettingsPageActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var usernameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var logoutButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings_page)

        // Init
        usernameField = findViewById(R.id.usernameField)
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        logoutButton = findViewById(R.id.logoutButton)
        backButton = findViewById(R.id.backButton)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()
        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        //val userId = sharedPreferences.getInt("user_id", -1)
        //if (userId != -1) {
        //  loadUserInfo(userId)
        //}

        logoutButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
            finish()
        }
    }

    private fun loadUserInfo(userId: Int) {
        lifecycleScope.launch {
            val user = userDao.getUserById(userId)
            user?.let {
                usernameField.setText(it.userName)
                emailField.setText(it.email)
                passwordField.setText(it.password)
            }
        }
    }
}