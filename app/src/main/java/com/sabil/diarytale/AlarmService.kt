package com.sabil.diarytale

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.IBinder
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import com.sabil.diarytale.App.Companion.SERVICE_NOTIF
import com.sabil.diarytale.alarm.AlarmReceiver
import java.util.*

class AlarmService:Service(){

    private lateinit var alarmSharedPref: SharedPreferences
    private lateinit var drinkSharedPref: SharedPreferences

    private var mTimeLeft: Long = 10000
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()

        alarmSharedPref = getSharedPreferences("ALARM",Context.MODE_PRIVATE)
        drinkSharedPref = getSharedPreferences("DRINK",Context.MODE_PRIVATE)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("service => running")

        val waktuBangun = alarmSharedPref.getLong("WAKTU_BANGUN",0L)
        val waktuTidur = alarmSharedPref.getLong("WAKTU_TIDUR",0L)
        val durasiJam = alarmSharedPref.getInt("DURASI_JAM",0)
        val durasiMenit = alarmSharedPref.getInt("DURASI_MENIT",0)

        val drinkLoop = drinkSharedPref.getInt("DRINK_LOOP",0)
        val mServiceRunning = drinkSharedPref.getBoolean("SERVICE",false)

        if(durasiJam == 0 && durasiMenit == 0 && !mServiceRunning){
            stopSelf()
        }
        else{
            if(durasiJam != 0 || durasiMenit != 0){
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
            }

            // Drink
            val drinkTanggal = drinkSharedPref.getLong("TANGGAL",0)
            val currentDate = Calendar.getInstance()
            currentDate.timeInMillis = drinkTanggal * 1000
            currentDate.add(Calendar.HOUR_OF_DAY,2)

            if(drinkLoop == 0){
                currentDate.add(Calendar.DATE,1)
            }

            val drinkAlarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val drinkIntent = Intent(this, DrinkReceiver::class.java).let {
                PendingIntent.getBroadcast(this,12,it,PendingIntent.FLAG_UPDATE_CURRENT)
            }

            drinkAlarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                currentDate.timeInMillis,
                drinkIntent
            )

            val serviceNotif = NotificationCompat.Builder(this,SERVICE_NOTIF)
                .setContentTitle("Running in foreground")
                .setContentText("Apps running in foreground")
                .setSmallIcon(R.drawable.ic_drink)
                .build()

            startForeground(123,serviceNotif)
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        val drinkLoop = drinkSharedPref.getInt("DRINK_LOOP",0)

        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}