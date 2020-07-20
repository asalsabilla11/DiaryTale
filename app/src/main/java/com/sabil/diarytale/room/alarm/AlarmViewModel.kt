package com.sabil.diarytale.room.alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AlarmViewModel(app: Application): AndroidViewModel(app) {

    private var repo: AlarmRepo = AlarmRepo(app)

    fun upsertAlarm(alarmEntity: AlarmEntity){
        repo.upsertAlarm(alarmEntity)
    }

    fun deletAll() = repo.deletAll()

    fun getAlarmData() = repo.getAlarmData()

}