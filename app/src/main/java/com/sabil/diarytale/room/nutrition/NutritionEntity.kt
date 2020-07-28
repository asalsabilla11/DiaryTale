package com.sabil.diarytale.room.nutrition

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "nutrisi")
data class NutritionEntity(
    @PrimaryKey
    val nutrisiID: String,
    val nutrisiNama: String,
    val nutrisiImage: String,
    val nutrisiKategori: String
)