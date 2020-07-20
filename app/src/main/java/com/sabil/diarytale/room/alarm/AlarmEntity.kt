package com.sabil.diarytale.room.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarm")
class AlarmEntity (
    @PrimaryKey
    val alarmID: String,
    val alarmBangun: Long,
    val alarmTidur: Long
)