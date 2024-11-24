package com.example.bitfit02

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_table")
data class NutritionEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "food") val food: String?,
    @ColumnInfo(name = "calories") val calories: String?
)