package com.sabil.diarytale.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import com.sabil.diarytale.room.alarm.AlarmEntity
import com.sabil.diarytale.room.alarm.AlarmViewModel
import java.util.*

class AddRecordService:Service(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate() {
        super.onCreate()

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)

        alarmViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AlarmViewModel::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        lifecycleRegistry.markState(Lifecycle.State.STARTED)

        val alarmID = UUID.randomUUID().toString()
        val waktuTidur = intent?.getLongExtra("WAKTU_TIDUR",0L)
        val waktuBangun = Calendar.getInstance().timeInMillis / 1000L
        val alarm = AlarmEntity(alarmID,waktuBangun, waktuTidur!!)
        alarmViewModel.upsertAlarm(alarm)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}