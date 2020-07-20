package com.sabil.diarytale.room.alarm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAlarm(alarmEntity: AlarmEntity)

    @Query("select * from alarm")
    fun getAlarmData(): LiveData<List<AlarmEntity>>
}