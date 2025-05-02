package com.example.pocketpaladinapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE userName = :userName AND password = :password")
    suspend fun login(userName: String, password: String): User?

    @Query("SELECT * FROM users WHERE userName = :userName")
    suspend fun getUserByUsername(userName: String): User?

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?


}
