package com.example.pocketpaladinapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetGoalDao {
    @Insert
    suspend fun insert(goal: BudgetGoal): Long

    @Query("SELECT * FROM budget_goals WHERE userOwnerId = :userId AND month = :month")
    suspend fun getGoalsForMonth(userId: Int, month: String): BudgetGoal?
}
