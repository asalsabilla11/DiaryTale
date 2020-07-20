package com.sabil.diarytale.alarm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import com.sabil.diarytale.AlarmService
import com.sabil.diarytale.R
import kotlinx.android.synthetic.main.activity_add_sleep_record.*
import java.util.*

class AddSleepRecordActivity : AppCompatActivity() {

    private lateinit var mCalendarBangun: Calendar
    private lateinit var mCalendarTidur: Calendar

    private var durasiJam: Int = 0
    private var durasiMenit: Int = 0

    private lateinit var alarmSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sleep_record)

        mCalendarTidur = Calendar.getInstance()
        mCalendarBangun = Calendar.getInstance()

        alarmSharedPref = getSharedPreferences("ALARM", Context.MODE_PRIVATE)

        indicator_tidur.text = DateFormat.format("HH:mm",mCalendarTidur).toString()
        timePicker_tidur.setIs24HourView(true)
        timePicker_tidur.setOnTimeChangedListener { timePicker, i, i2 ->
            mCalendarTidur.set(Calendar.HOUR_OF_DAY,i)
            mCalendarTidur.set(Calendar.MINUTE,i2)
            timePicker.setIs24HourView(true)

            setDurasiTidur()
            indicator_tidur.text = DateFormat.format("HH:mm",mCalendarTidur).toString()
        }


        indicator_bangun.text = DateFormat.format("HH:mm",mCalendarBangun).toString()
        timePicker_bangun.setIs24HourView(true)
        timePicker_bangun.setOnTimeChangedListener { timePicker, i, i2 ->
            mCalendarBangun.set(Calendar.HOUR_OF_DAY,i)
            mCalendarBangun.set(Calendar.MINUTE,i2)
            timePicker.setIs24HourView(true)

            setDurasiTidur()
            indicator_bangun.text = DateFormat.format("HH:mm",mCalendarBangun).toString()
        }

        btn_done_addSleep.setOnClickListener {
            addAlarmToSharedPref()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun addAlarmToSharedPref(){
        val waktuTidur = mCalendarTidur.timeInMillis / 1000L
        val waktuBangun = mCalendarBangun.timeInMillis / 1000L

        val editor = alarmSharedPref.edit().clear()
        editor.putLong("WAKTU_TIDUR",waktuTidur)
        editor.putLong("WAKTU_BANGUN",waktuBangun)
        editor.putInt("DURASI_JAM",durasiJam)
        editor.putInt("DURASI_MENIT",durasiMenit)
        editor.apply()

        val serviceIntent = Intent(this, AlarmService::class.java)
        startService(serviceIntent)

        super.onBackPressed()
    }

    private fun setDurasiTidur(){
        val jamTidur = mCalendarTidur.get(Calendar.HOUR_OF_DAY)
        val menitTidur = mCalendarTidur.get(Calendar.MINUTE)
        val jamBangun = mCalendarBangun.get(Calendar.HOUR_OF_DAY)
        val menitBangun = mCalendarBangun.get(Calendar.MINUTE)

        if(jamTidur == 0){
            durasiJam = jamBangun
        }
        else if(jamTidur <= jamBangun){
            durasiJam = jamBangun - jamTidur
        }
        else{
            durasiJam = (24 - jamTidur) + jamBangun
        }

        if(menitBangun >= menitTidur){
            durasiMenit = menitBangun - menitTidur
        }
        else if(menitBangun <= menitTidur){
            durasiMenit = menitBangun + menitTidur
            durasiJam = durasiJam - 1
        }
        else{
            durasiMenit = menitTidur- menitBangun
        }

        if(durasiMenit >= 60){
            durasiMenit = durasiMenit - 60
            durasiJam = durasiJam + 1
        }
        durasiTidur_jam.text = durasiJam.toString()
        durasiTidur_menit.text = durasiMenit.toString()
    }
}