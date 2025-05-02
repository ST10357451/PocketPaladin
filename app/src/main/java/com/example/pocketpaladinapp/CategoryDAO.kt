package com.example.pocketpaladinapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category): Long

    @Query("SELECT * FROM categories WHERE userOwnerId = :userId")
    suspend fun getAllCategories(userId: Int): List<Category>

    @Query("SELECT * FROM categories WHERE categoryName = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): Category?

}
