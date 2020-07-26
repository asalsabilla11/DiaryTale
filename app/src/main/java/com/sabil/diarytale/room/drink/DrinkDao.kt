package com.sabil.diarytale.room.drink

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun drinkUpsert(drinkEntity: DrinkEntity)

    @Query("delete from drink")
    fun drinkDeleteALl()
    @Query("delete from drink where drinkID = :drinkID")
    fun drinkDeleteByID(drinkID: String)

    @Query("select * from drink order by drinkTimestamp asc")
    fun drinkGetAll(): LiveData<List<DrinkEntity>>
}