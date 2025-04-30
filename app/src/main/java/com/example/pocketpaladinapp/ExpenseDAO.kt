package com.example.pocketpaladinapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense): Long

    @Query("SELECT * FROM expenses WHERE userOwnerId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesInPeriod(userId: Int, startDate: String, endDate: String): List<Expense>

    @Query("""
        SELECT categoryOwnerId, SUM(amount) as totalAmount 
        FROM expenses 
        WHERE userOwnerId = :userId AND date BETWEEN :startDate AND :endDate 
        GROUP BY categoryOwnerId
    """)
    suspend fun getTotalSpentByCategory(userId: Int, startDate: String, endDate: String): List<CategoryTotal>
}

data class CategoryTotal(
    val categoryOwnerId: Int,
    val totalAmount: Double
)
