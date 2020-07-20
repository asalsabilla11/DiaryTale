package com.sabil.diarytale.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.alarm.AlarmEntity
import kotlinx.android.synthetic.main.alarm_layout_rv.view.*
import java.util.*

class AlarmAdaper: RecyclerView.Adapter<AlarmAdaper.ViewHolder>() {

    private lateinit var alarmList: List<AlarmEntity>

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val tvJamTidur = view.tv_jamTidur_alarmLayout
        val tvJamBangun = view.tv_jamBangun_alarmLayout
        val tvDurasiJam = view.tv_durasiJam_alarmLayout
        val tvDurasiMenit = view.tv_durasiMenit_alarmLayout

        fun bind(alarmEntity: AlarmEntity){
            val timeBangun = Calendar.getInstance()
            timeBangun.timeInMillis = alarmEntity.alarmBangun * 1000L
            tvJamBangun.text = DateFormat.format("HH:mm",timeBangun).toString()

            val timeTidur = Calendar.getInstance()
            timeTidur.timeInMillis = alarmEntity.alarmTidur * 1000L
            tvJamTidur.text = DateFormat.format("HH:mm",timeTidur).toString()

            val jamTidur = timeTidur.get(Calendar.HOUR_OF_DAY)
            val menitTidur = timeTidur.get(Calendar.MINUTE)
            val jamBangun = timeBangun.get(Calendar.HOUR_OF_DAY)
            val menitBangun = timeBangun.get(Calendar.MINUTE)

            var durasiJam = 0
            var durasiMenit = 0

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
            tvDurasiJam.text = durasiJam.toString()
            tvDurasiMenit.text = durasiMenit.toString()
        }
    }

    fun alarmAdapter(alarmList: List<AlarmEntity>){
        this.alarmList = alarmList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.alarm_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alarmList.get(position))
    }
}