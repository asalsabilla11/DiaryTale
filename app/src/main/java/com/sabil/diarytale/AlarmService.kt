package com.sabil.diarytale

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import com.sabil.diarytale.App.Companion.SERVICE_NOTIF
import com.sabil.diarytale.alarm.AlarmReceiver
import java.util.*

class AlarmService:Service(){

    private lateinit var alarmSharedPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        alarmSharedPref = getSharedPreferences("ALARM",Context.MODE_PRIVATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val waktuBangun = alarmSharedPref.getLong("WAKTU_BANGUN",0L)
        val waktuTidur = alarmSharedPref.getLong("WAKTU_TIDUR",0L)

        if(waktuBangun != 0L){
            val calWaktuBangun = Calendar.getInstance()
            calWaktuBangun.timeInMillis = waktuBangun * 1000L

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val alarmIntent = Intent(this,
                AlarmReceiver::class.java).let {
                it.putExtra("WAKTU_TIDUR",waktuTidur)
                PendingIntent.getBroadcast(this,1,it,PendingIntent.FLAG_UPDATE_CURRENT)
            }

            alarmManager?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calWaktuBangun.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )

            val cek = DateFormat.format("HH:mm",calWaktuBangun).toString()

            val serviceNotif = NotificationCompat.Builder(this,SERVICE_NOTIF)
                .setContentTitle(cek)
                .setContentText("Apps running in foreground")
                .setSmallIcon(R.drawable.ic_drink)
                .build()

            startForeground(123,serviceNotif)
        }
        else{
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}