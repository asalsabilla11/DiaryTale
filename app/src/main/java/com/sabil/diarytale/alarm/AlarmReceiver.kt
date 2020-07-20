package com.sabil.diarytale.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sabil.diarytale.App.Companion.WAKEUP_NOTIF
import com.sabil.diarytale.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val waktuTidur = intent!!.getLongExtra("WAKTU_TIDUR",0L)

        val wakeUpNotif = NotificationCompat.Builder(context!!,WAKEUP_NOTIF)
            .setContentTitle("Waktunya bangun!!")
            .setContentText("Apakah anda sudah bangun?")
            .setSmallIcon(R.drawable.ic_drink)
            .setContentIntent(onSwapNotif(context,waktuTidur))
            .setDeleteIntent(onSwapNotif(context,waktuTidur))
            .build()

        NotificationManagerCompat.from(context).notify(1,wakeUpNotif)

//        val serviceIntent = Intent(context,AlarmService::class.java)
//        context.stopService(serviceIntent)
    }

    private fun onSwapNotif(context: Context?,waktuTidur: Long): PendingIntent?{
        val inputData = Intent(context, AddRecordService::class.java).let {
            it.putExtra("WAKTU_TIDUR",waktuTidur)
            PendingIntent.getService(context,99,it,PendingIntent.FLAG_ONE_SHOT)
        }

        return inputData
    }
}