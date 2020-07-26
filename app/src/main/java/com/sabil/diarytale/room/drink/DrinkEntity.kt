package com.sabil.diarytale.room.drink

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink")
data class DrinkEntity (
    @PrimaryKey
    val drinkID: String,
    val howMuchDrink: Int,
    val drinkTimestamp: Long
)