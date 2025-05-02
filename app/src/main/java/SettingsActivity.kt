package com.example.budgetapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketpaladinapp.AppDatabase
import com.example.pocketpaladinapp.MainActivity
import com.example.pocketpaladinapp.R
import com.example.pocketpaladinapp.UserDao
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity()
{
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
        setContentView(R.layout.settingspage)

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
            startActivity(Intent(this, MainActivity::class.java)) //Change to Category Activity
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)) //Change to Login Activity
            finish()
        }
    }

   // private fun loadUserInfo(userId: Int) {
     //   lifecycleScope.launch {
       //     val user = userDao.getUserById(userId)
         //   user?.let {
           //     usernameField.setText(it.username)
             //   emailField.setText(it.email)
               // passwordField.setText(it.password)
            //}
        //}
    //}

}