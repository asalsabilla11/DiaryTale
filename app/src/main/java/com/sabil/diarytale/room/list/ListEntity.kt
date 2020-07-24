package com.sabil.diarytale.room.list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class ListEntity(
    @PrimaryKey
    val listID:String,
    val listTitle: String,
    val listTimestamp: Long,
    val listDone: Boolean,
    val allDay: Boolean
)