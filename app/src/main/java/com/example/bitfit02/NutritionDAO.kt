package com.example.bitfit02

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDAO {
    @Query("SELECT * FROM nutrition_table")
    fun getAll(): Flow<List<NutritionEntity>>

    @Insert fun insertAll(foods: List<NutritionEntity>)

    @Insert fun insert(food: NutritionEntity)

    @Query("DELETE FROM nutrition_table")
    fun deleteAll()
}