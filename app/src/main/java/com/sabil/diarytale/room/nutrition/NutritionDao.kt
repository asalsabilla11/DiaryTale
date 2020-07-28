package com.sabil.diarytale.room.nutrition

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(nutrisiEntity: NutritionEntity)

    @Query("select * from nutrisi where nutrisiKategori = :nutrisiKategori")
    fun getNutrition(nutrisiKategori: String): LiveData<List<NutritionEntity>>
}