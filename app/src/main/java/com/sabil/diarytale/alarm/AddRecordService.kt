package com.sabil.diarytale.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import com.sabil.diarytale.room.alarm.AlarmEntity
import com.sabil.diarytale.room.alarm.AlarmViewModel
import com.sabil.diarytale.room.drink.DrinkEntity
import com.sabil.diarytale.room.drink.DrinkViewModel
import java.util.*

class AddRecordService:Service(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var drinkSharedpref: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)

        alarmViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AlarmViewModel::class.java)
        drinkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(DrinkViewModel::class.java)
        drinkSharedpref = getSharedPreferences("DRINK",Context.MODE_PRIVATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        lifecycleRegistry.markState(Lifecycle.State.STARTED)

        val siapa = intent?.getStringExtra("SIAPA")
        var drinkTotal = drinkSharedpref.getInt("DRINK_TOTAL",0)
        val drinkTimestamp = drinkSharedpref.getLong("TANGGAL",0)
        var drinkID = drinkSharedpref.getString("DRINK_ID",null)
        if(siapa == "DRINK"){
            if(drinkID == null){
                drinkID = UUID.randomUUID().toString()
            }

            if(drinkTotal == 0){
                drinkTotal = 1
            }
            val increstDrink = drinkTotal + 1
            val editor = drinkSharedpref.edit()
            editor.putString("DRINK_ID",drinkID)
            editor.putInt("DRINK_TOTAL",increstDrink)
            editor.apply()
            editor.commit()

            val drinks = DrinkEntity(drinkID,drinkTotal,drinkTimestamp)
            drinkViewModel.drinkUpsert(drinks)
        }

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