package com.sabil.diarytale.room.alarm

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AlarmRepo(app: Application):CoroutineScope {

    private var alarmDao: AlarmDao

    init {
        val db = DiaryTaleDB.getInstance(app)
        alarmDao = db.alarmDao
    }

    override val coroutineContext: CoroutineContext
        get() = IO


    fun upsertAlarm(alarmEntity: AlarmEntity){
        launch {
            upsertAlarmBG(alarmEntity)
        }
    }

    private suspend fun upsertAlarmBG(alarmEntity: AlarmEntity){
        withContext(IO){
            alarmDao.upsertAlarm(alarmEntity)
        }
    }

    fun deletAll(){
        launch {
            deletAllBG()
        }
    }

    private suspend fun deletAllBG(){
        withContext(IO){
            alarmDao.deleteAll()
        }
    }

    fun getAlarmData() = alarmDao.getAlarmData()
}