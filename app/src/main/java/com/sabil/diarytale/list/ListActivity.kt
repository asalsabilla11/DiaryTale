package com.sabil.diarytale.list

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabil.diarytale.R
import com.sabil.diarytale.adapter.TodoListAdapter
import com.sabil.diarytale.adapter.UpComeListAdapter
import com.sabil.diarytale.room.list.ListEntity
import com.sabil.diarytale.room.list.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*
import kotlin.collections.ArrayList


class ListActivity : AppCompatActivity(), TodoListAdapter.ItemClickListener,UpComeListAdapter.ItemClickListener {

    private lateinit var listCalendar: Calendar
    private var pilihKategori: Boolean = false
    private var allDay: Boolean = false

    private lateinit var listViewModel: ListViewModel
    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var upcomeListAdapter: UpComeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        todoListAdapter = TodoListAdapter()
        upcomeListAdapter = UpComeListAdapter()

        getData()

        wibu.setOnClickListener {
            et_addList_list.clearFocus()
        }

        et_addList_list.setOnFocusChangeListener { view, b ->
            when (b) {
                true -> hs_kategoriJam_list.visibility = View.VISIBLE
                false -> hs_kategoriJam_list.visibility = View.GONE
            }
        }

        btn_addList_list.isEnabled = false
        et_addList_list.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btn_addList_list.isEnabled = !(p0.isNullOrBlank() || p0.isNullOrEmpty())
                if (btn_addList_list.isEnabled == false) {
                    btn_addList_list.setBackgroundResource(R.drawable.ic_baseline_add_circle_disable)
                } else btn_addList_list.setBackgroundResource(R.drawable.ic_baseline_add_circle_24)
            }
        })

        btn_addList_list.setOnClickListener {
            if(pilihKategori == true){
                clearBtnBackground()
                setData()
//                val imm: InputMethodManager =
//                    this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                val view: View = this.getCurrentFocus()!!
//                imm.hideSoftInputFromWindow(view.windowToken, 0)
//                et_addList_list.let {
//                    it.text = null
//                    it.clearFocus()
//                }
                et_addList_list.text = null
                btn_hariApapun_list.text = "Hari apapun"
                btn_custom_list.text = "Custom"
            }else{
                Toast.makeText(this, "Anda belum mentukan jam/tanggal/hari", Toast.LENGTH_SHORT).show()
            }
            pilihKategori = false
            allDay = false
        }

        toolbar_list.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.deleteAllDoneList_listMenu ->{
                    listViewModel.listDeleteAllDone()
                }
                R.id.deleteAllList_listMenu ->{
                    listViewModel.listDeleteAll()
                }
            }
            true
        }
    }

    fun setData(){
        val listID = UUID.randomUUID().toString()
        val listTitle = et_addList_list.text.toString()
        val listTimestamp = listCalendar.timeInMillis / 1000
        val listDone = false
        val lists = ListEntity(listID,listTitle,listTimestamp,listDone,allDay)
        listViewModel.listUpsert(lists)
    }
    private fun getData(){
        listViewModel.getAllList().observe(this,Observer<List<ListEntity>>{list->
            val arrayListToday = ArrayList<ListEntity>()
            val arrayListUpcome = ArrayList<ListEntity>()

            val currentDate = Calendar.getInstance().get(Calendar.DATE)
            list.forEach {data->
                val listdate = Calendar.getInstance()
                listdate.timeInMillis = data.listTimestamp * 1000
                if(listdate.before(Calendar.getInstance())){
                    listViewModel.listDeleteOne(data.listID)
                }

                val listCal = Calendar.getInstance()
                listCal.timeInMillis = data.listTimestamp * 1000
                val listDate = listCal.get(Calendar.DATE)

                if(listDate == currentDate){
                    arrayListToday.add(data)
                }
                else{
                    arrayListUpcome.add(data)
                }
            }
            todoListAdapter.todoListAdapter(arrayListToday,this)
            rv_list.layoutManager = LinearLayoutManager(this)
            rv_list.adapter = todoListAdapter

            upcomeListAdapter.upComeListAdapter(arrayListUpcome,this)
            rv_listUpcome_list.layoutManager = LinearLayoutManager(this)
            rv_listUpcome_list.adapter = upcomeListAdapter
        })
    }

    fun clearBtnBackground(){
        btn_besokPagi_list.setBackgroundColor(Color.parseColor("#003a4664"))
        btn_besokPagi_list.setTextColor(Color.parseColor("#3a4664"))

        btn_besokSore_list.setBackgroundColor(Color.parseColor("#003a4664"))
        btn_besokSore_list.setTextColor(Color.parseColor("#3a4664"))

        btn_hariApapun_list.setBackgroundColor(Color.parseColor("#003a4664"))
        btn_hariApapun_list.setTextColor(Color.parseColor("#3a4664"))

        btn_custom_list.setBackgroundColor(Color.parseColor("#003a4664"))
        btn_custom_list.setTextColor(Color.parseColor("#3a4664"))
    }

    fun kategoriWaktu(view: View) {
        clearBtnBackground()
        when (view.id) {
            R.id.btn_besokPagi_list -> {
                btn_besokPagi_list.setBackgroundColor(Color.parseColor("#3a4664"))
                btn_besokPagi_list.setTextColor(Color.parseColor("#ffffff"))

                listCalendar = Calendar.getInstance()
                listCalendar.set(Calendar.HOUR_OF_DAY,9)
                listCalendar.set(Calendar.MINUTE,0)
                listCalendar.set(Calendar.SECOND,0)
                listCalendar.add(Calendar.DATE,1)
            }
            R.id.btn_besokSore_list -> {
                btn_besokSore_list.setBackgroundColor(Color.parseColor("#3a4664"))
                btn_besokSore_list.setTextColor(Color.parseColor("#ffffff"))

                listCalendar = Calendar.getInstance()
                listCalendar.set(Calendar.HOUR_OF_DAY,16)
                listCalendar.set(Calendar.MINUTE,0)
                listCalendar.set(Calendar.SECOND,0)
                listCalendar.add(Calendar.DATE,1)
            }
            R.id.btn_hariApapun_list -> {
                btn_hariApapun_list.setBackgroundColor(Color.parseColor("#3a4664"))
                btn_hariApapun_list.setTextColor(Color.parseColor("#ffffff"))

                dialogDate("")
            }
            R.id.btn_custom_list -> {
                btn_custom_list.setBackgroundColor(Color.parseColor("#3a4664"))
                btn_custom_list.setTextColor(Color.parseColor("#ffffff"))

                dialogDate("custom")
            }
        }
        pilihKategori = true
    }

    private fun dialogDate(kategori: String) {
        btn_hariApapun_list.text = "Hari apapun"
        btn_custom_list.text = "Custom"
        val datePickerListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            listCalendar = Calendar.getInstance()
            listCalendar.set(Calendar.YEAR, i)
            listCalendar.set(Calendar.MONTH, i2)
            listCalendar.set(Calendar.DATE, i3)
            listCalendar.set(Calendar.HOUR,0)
            listCalendar.set(Calendar.MINUTE,0)
            listCalendar.set(Calendar.SECOND,0)

            if(kategori == "custom"){
                dialogTime()
            }else{
                allDay = true
                btn_hariApapun_list.text = DateFormat.format("dd MMM yyyy",listCalendar).toString()
            }
        }
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            datePickerListener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE,"CANCEL"){p0,p1->
            clearBtnBackground()
            pilihKategori = false
        }
        datePickerDialog.show()
    }

    private fun dialogTime() {
        val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
            listCalendar.set(Calendar.HOUR,i)
            listCalendar.set(Calendar.MINUTE,i2)

            btn_custom_list.text = DateFormat.format("dd MMM yyyy HH:mm",listCalendar).toString()
        }
        val currentTime = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(this,timePickerListener,
            currentTime.get(Calendar.HOUR),
            currentTime.get(Calendar.MINUTE),true)

        timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE,"CANCEL"){p0,p1->
            clearBtnBackground()
            pilihKategori = false
        }
        timePickerDialog.show()
    }

    override fun itemCLickListener(listEntity: ListEntity,cbCondition: Boolean) {
        listViewModel.updateDone(cbCondition,listEntity.listID)
    }

    override fun upcomeItemCLickListener(listEntity: ListEntity,cbCondition: Boolean) {
        listViewModel.updateDone(cbCondition,listEntity.listID)
    }

    override fun btnDeleteClick(listEntity: ListEntity) {
        listViewModel.listDeleteOne(listEntity.listID)
    }

    override fun upcomeDeleteClick(listEntity: ListEntity) {
        listViewModel.listDeleteOne(listEntity.listID)
    }

}