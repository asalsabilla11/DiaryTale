package com.sabil.diarytale.drink

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabil.diarytale.AlarmService
import com.sabil.diarytale.R
import com.sabil.diarytale.adapter.DrinkAdapter
import com.sabil.diarytale.room.drink.DrinkEntity
import com.sabil.diarytale.room.drink.DrinkViewModel
import kotlinx.android.synthetic.main.activity_drink.*
import java.util.*

class DrinkActivity : AppCompatActivity() {

    private lateinit var drinkSharedPref: SharedPreferences
    private lateinit var drinkAdapter: DrinkAdapter
    private lateinit var drinkViewModel: DrinkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        setSupportActionBar(toolbar_drink)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drinkSharedPref = getSharedPreferences("DRINK",Context.MODE_PRIVATE)
        drinkAdapter = DrinkAdapter()
        drinkViewModel = ViewModelProviders.of(this).get(DrinkViewModel::class.java)


        updateButton()
        btn_startReminder_drink.setOnClickListener {
            val intentService = Intent(this,AlarmService::class.java)
            val mServiceRunning = drinkSharedPref.getBoolean("SERVICE",false)
            if(mServiceRunning == false){
                val editor = drinkSharedPref.edit()
                val currentDay = Calendar.getInstance()
                val limitDay = Calendar.getInstance()
                limitDay.set(Calendar.HOUR_OF_DAY,8)
                limitDay.set(Calendar.MINUTE,0)
                limitDay.set(Calendar.SECOND,0)

                if(currentDay.timeInMillis / 1000 > limitDay.timeInMillis / 1000){
                    currentDay.add(Calendar.DATE,1)
                }
                else{
                    currentDay.set(Calendar.HOUR_OF_DAY,5)
                    currentDay.set(Calendar.MINUTE,0)
                    currentDay.set(Calendar.SECOND,0)
                }

                editor.putInt("DRINK_LOOP",8)
                editor.putInt("DRINK_TOTAL",0)
                editor.putString("DRINK_ID",null)
                editor.putLong("TANGGAL",currentDay.timeInMillis / 1000)
                editor.putBoolean("SERVICE",true)
                editor.apply()
                startService(intentService)
            }
            else{
                val editor = drinkSharedPref.edit()
                editor.putBoolean("SERVICE",false)
                editor.apply()
                stopService(intentService)
            }
            updateButton()
        }
    }

    override fun onStart() {
        super.onStart()
        getDataDrink()
    }

    private fun updateButton(){
        val mServiceRunning = drinkSharedPref.getBoolean("SERVICE",false)
        if(!mServiceRunning){
            btn_startReminder_drink.text = "Start Reminder"
        }else btn_startReminder_drink.text = "Stop Reminder"
    }

    private fun getDataDrink(){
        drinkViewModel.drinkGetAll().observe(this, Observer<List<DrinkEntity>>{
            println("asd => $it")
            drinkAdapter.drinkAdapter(it)
            rv_drinkRecord_drink.layoutManager = LinearLayoutManager(this)
            rv_drinkRecord_drink.adapter = drinkAdapter
        })
    }
}