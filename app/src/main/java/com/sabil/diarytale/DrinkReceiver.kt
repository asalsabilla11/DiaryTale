package com.sabil.diarytale

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sabil.diarytale.App.Companion.DRINK_NOTIF
import com.sabil.diarytale.alarm.AddRecordService

class DrinkReceiver: BroadcastReceiver() {

    private lateinit var drinkSharedPref: SharedPreferences

    override fun onReceive(p0: Context?, p1: Intent?) {

        drinkSharedPref = p0!!.getSharedPreferences("DRINK",Context.MODE_PRIVATE)

        val drinkLoop = drinkSharedPref.getInt("DRINK_LOOP",0)
        val intenService = Intent(p0,AlarmService::class.java)

        val drinkNotif = NotificationCompat.Builder(p0,DRINK_NOTIF)
            .setContentTitle("Waktunya minum!!!")
            .setContentText("Apakah kamu sudah minum?")
            .addAction(0,"SUDAH",clickedPendingIntent(p0,drinkLoop))
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_drink)
            .build()

        NotificationManagerCompat.from(p0).notify(drinkLoop,drinkNotif)


        val newLoop = drinkLoop - 1
        val editor = drinkSharedPref.edit()
        editor.putInt("DRINK_LOOP",newLoop)
        editor.apply()
        editor.commit()
        p0.stopService(intenService)
        p0.startService(intenService)

    }

    private fun clickedPendingIntent(context: Context,drinkLoop: Int): PendingIntent?{
        NotificationManagerCompat.from(context).cancel(drinkLoop)
        val notifDeleted = Intent(context,AddRecordService::class.java).let {
            it.putExtra("SIAPA","DRINK")
            PendingIntent.getService(context,drinkLoop,it,PendingIntent.FLAG_ONE_SHOT)
        }
        return notifDeleted
    }
}