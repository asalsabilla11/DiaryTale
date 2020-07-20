package com.sabil.diarytale

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabil.diarytale.adapter.AlarmAdaper
import com.sabil.diarytale.room.alarm.AlarmEntity
import com.sabil.diarytale.room.alarm.AlarmViewModel
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var mCalendarBangun: Calendar
    private lateinit var mCalendarTidur: Calendar

    private lateinit var alarmSharedPref: SharedPreferences

    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var alarmAdaper: AlarmAdaper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmSharedPref = getSharedPreferences("ALARM", Context.MODE_PRIVATE)

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)

        // Init waktu tidur
        mCalendarTidur = Calendar.getInstance()
        mCalendarTidur.apply {
            set(Calendar.HOUR_OF_DAY,23)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
        }
        mCalendarBangun = Calendar.getInstance()
        mCalendarBangun.apply {
            set(Calendar.HOUR_OF_DAY,7)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
        }

        btn_addSleep_alarm.setOnClickListener {
            startActivity(Intent(this,AddSleepRecordActivity::class.java))
        }

        alarmViewModel.getAlarmData().observe(this, Observer<List<AlarmEntity>>{listAlarm->
            listAlarm.forEach {
                println("data_alarm => $it")
            }
        })

        btn_deleteAll_alarm.setOnClickListener {
            alarmViewModel.deletAll()
        }

    }

    override fun onStart() {
        super.onStart()
        getDataAlarm()
        val durasiJam = alarmSharedPref.getInt("DURASI_JAM",0)
        val durasiMenit = alarmSharedPref.getInt("DURASI_MENIT",0)
        tv_jam_alarm.text = durasiJam.toString()
        tv_menit_alarm.text = durasiMenit.toString()
    }

    private fun getDataAlarm(){
        alarmViewModel.getAlarmData().observe(this,Observer<List<AlarmEntity>>{
            setRecyclerView(it)
        })
    }

    private fun setRecyclerView(alarmList: List<AlarmEntity>){
        alarmAdaper = AlarmAdaper()
        alarmAdaper.alarmAdapter(alarmList)
        rv_resutlAlarm_alarm.layoutManager = LinearLayoutManager(this)
        rv_resutlAlarm_alarm.adapter = alarmAdaper
    }

    private fun setDurasiTidur(){
        val jamTidur = mCalendarTidur.get(Calendar.HOUR_OF_DAY)
        val menitTidur = mCalendarTidur.get(Calendar.MINUTE)
        val jamBangun = mCalendarBangun.get(Calendar.HOUR_OF_DAY)
        val menitBangun = mCalendarBangun.get(Calendar.MINUTE)
        var durasiTidur = 0
        var durasiMenit = 0

        if(jamTidur == 0){
            durasiTidur = jamBangun
        }
        else if(jamTidur <= jamBangun){
            durasiTidur = jamBangun - jamTidur
        }
        else{
            durasiTidur = (24 - jamTidur) + jamBangun
        }

        if(menitBangun >= menitTidur){
            durasiMenit = menitBangun - menitTidur
        }
        else if(menitBangun <= menitTidur){
            durasiMenit = menitBangun + menitTidur
            durasiTidur = durasiTidur - 1
        }
        else{
            durasiMenit = menitTidur- menitBangun
        }

        if(durasiMenit >= 60){
            durasiMenit = durasiMenit - 60
            durasiTidur = durasiTidur + 1
        }
        tv_jam_alarm.text = durasiTidur.toString()
        tv_menit_alarm.text = durasiMenit.toString()
    }
}