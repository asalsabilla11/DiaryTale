package com.sabil.diarytale.room.bank

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankEntity (
    @PrimaryKey
    val bankID: String,
    val bankNominal: Long,
    val bankDesc: String,
    val type: String,
    val timestamp: Long
)