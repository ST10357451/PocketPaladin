package com.example.pocketpaladinapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_goals",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userOwnerId")]
)
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true) val goalId: Int = 0,
    val userOwnerId: Int,
    val month: String, // e.g., "2025-04"
    val minGoal: Double,
    val maxGoal: Double
)
