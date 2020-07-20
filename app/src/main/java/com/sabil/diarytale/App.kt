package com.sabil.diarytale

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class App: Application() {

    companion object{
        const val SLEEP_NOTIF = "com.sabil.diarytale_sleep-notif"
        const val WAKEUP_NOTIF = "com.sabil.diarytale_wakeup-notif"
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val sleepNotificationChannel = NotificationChannel(
                SLEEP_NOTIF,
                "Sleep Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val wakeupNotificationChannel = NotificationChannel(
                WAKEUP_NOTIF,
                "Wakeup Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(sleepNotificationChannel)
            notificationManager.createNotificationChannel(wakeupNotificationChannel)
        }
    }


}