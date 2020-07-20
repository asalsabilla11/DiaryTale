package com.sabil.diarytale

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var mCalendarBangun: Calendar
    private lateinit var mCalendarTidur: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)


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

        btn_waktuTidur_alarm.setOnClickListener {
            setWaktuTidur()
        }
        btn_waktuBangun_alarm.setOnClickListener {
            setWaktuBangun()
        }

        btn_waktuTidur_alarm.text = DateFormat.format("HH:mm",mCalendarTidur).toString()
        btn_waktuBangun_alarm.text = DateFormat.format("HH:mm",mCalendarBangun).toString()
        setDurasiTidur()


        sw_active_alarm.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            when(b){
                true-> Toast.makeText(this,"Switch On",Toast.LENGTH_SHORT).show()
                else-> Toast.makeText(this,"Switch Off",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setWaktuTidur(){
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                mCalendarTidur.set(Calendar.HOUR_OF_DAY,i)
                mCalendarTidur.set(Calendar.MINUTE,i2)

                btn_waktuTidur_alarm.text = DateFormat.format("HH:mm",mCalendarTidur).toString()
                setDurasiTidur()
            },
            mCalendarTidur.get(Calendar.HOUR_OF_DAY),
            mCalendarTidur.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun setWaktuBangun(){
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                mCalendarBangun.set(Calendar.HOUR_OF_DAY,i)
                mCalendarBangun.set(Calendar.MINUTE,i2)

                btn_waktuBangun_alarm.text = DateFormat.format("HH:mm",mCalendarBangun).toString()
                setDurasiTidur()
            },
            mCalendarBangun.get(Calendar.HOUR_OF_DAY),
            mCalendarBangun.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
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