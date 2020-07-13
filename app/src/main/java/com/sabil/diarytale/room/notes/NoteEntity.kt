package com.sabil.diarytale.room.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey
    val noteID: String,
    val noteTitle: String,
    val noteIsi: String,
    val timestamp: Long
)