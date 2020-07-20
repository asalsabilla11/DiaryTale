package com.sabil.diarytale

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sabil.diarytale.adapter.BankAdapter
import com.sabil.diarytale.room.bank.BankEntity
import com.sabil.diarytale.room.bank.BankViewModel
import kotlinx.android.synthetic.main.activity_money.*
import kotlinx.android.synthetic.main.dialog_bank_layout.view.*
import java.util.*

class MoneyActivity : AppCompatActivity(), BankAdapter.ItemClickListener {

    private lateinit var mBankViewModel: BankViewModel
    private lateinit var mBankAdapter: BankAdapter
    private lateinit var mPengeluaran: String

    private var editAndDelete: Boolean = false
    private lateinit var bankID: String
    private var bankNominal: Long = 0
    private lateinit var bankDesc: String
    private lateinit var bankType: String
    private var mTimestamp: Long = 0

    private var tambahBeres: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        mBankViewModel = ViewModelProviders.of(this).get(BankViewModel::class.java)
        mBankAdapter = BankAdapter()

        mPengeluaran = "in"

        setSupportActionBar(toolbar_money)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_money.bringToFront()

        tablayout_money.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var mtype = ""
                when(tab?.position){
                    0-> mtype = "in"
                    else-> mtype = "out"
                }
                getDataBank(mtype)
            }
        })

        mBankViewModel.getTotalNominalIn().observe(this, Observer {nominalIn->
            println("nominalIn: $nominalIn")
            nominalIn.let {
                mBankViewModel.getTotalNominalOut().observe(this, Observer {nominalOut->
                    println("nominalOut: $nominalOut")
                    var total = 0
                    if(nominalOut == null && it != null){
                        total = it
                    }else if(it == null && nominalOut != null){
                        total = nominalOut
                    }
                    else if(nominalOut != null && it != null){
                        total = it - nominalOut
                    } else total = 0

                    println("nominal: $total")
                    val halo = String.format("%,d",total)
                    tv_bankNominal_money.text = halo
                })
            }
        })

        btn_add_money.setOnClickListener {
            showDialogTambah()
        }

        btn_deleteAll_money.setOnClickListener {
            mBankViewModel.clearHistory()
        }
    }

    override fun onStart() {
        super.onStart()
        val mType = "in"
        getDataBank(mType)
    }

    private fun getDataBank(type: String){
        mBankViewModel.getDataBankByType(type).observe(this,Observer<List<BankEntity>>{
            tampilDataKeRecyclerView(it)
        })
    }

    private fun tampilDataKeRecyclerView(bankList: List<BankEntity>){
        mBankAdapter.bankAdapter(bankList,this)
        rv_bankInOut_money.apply {
            layoutManager = LinearLayoutManager(this@MoneyActivity)
            adapter = mBankAdapter
        }
    }

    private fun validasiFromNominal(
        et_nominal: TextInputEditText,
        etLayout_nominal: TextInputLayout,
        et_desc : TextInputEditText,
        etLayout_desc: TextInputLayout,
        type: String
    ){
        if(et_desc.text.isNullOrEmpty()){
            etLayout_desc.isErrorEnabled = true
            etLayout_desc.error = "Nominal tidak boleh kosong"
            return
        }
        etLayout_desc.isErrorEnabled = false

        if(et_nominal.text.isNullOrEmpty()){
            etLayout_nominal.isErrorEnabled = true
            etLayout_nominal.error = "Nominal tidak boleh kosong"
            return
        }
        etLayout_nominal.isErrorEnabled = false

        val bankNominal = et_nominal.text.toString().toLong()
        val bankDesc = et_desc.text.toString()
        setDataToDatabase(bankNominal,bankDesc,type)
    }

    private fun setDataToDatabase(
        bankNominal: Long,
        bankDesc: String,
        type: String
    ){
        val bankID: String
        if(editAndDelete == false){
            bankID= UUID.randomUUID().toString()
        }else bankID = this.bankID
        val timestamp = Calendar.getInstance().timeInMillis / 1000L
        val bankData = BankEntity(bankID,bankNominal,bankDesc,type,timestamp)
        mBankViewModel.upsert(bankData)


        tambahBeres = true
        editAndDelete = false
    }

    @SuppressLint("InflateParams")
    private fun showDialogTambah(){
        val builder = AlertDialog.Builder(this,R.style.MyDialogTheme)
        val view = this.layoutInflater.inflate(R.layout.dialog_bank_layout,null)
        val et_nominal = view.et_nominal_dialogBank
        val etLayout_nominal = view.etLayout_nominal_dialogBank
        val et_desc = view.et_desc_dialogBank
        val etLayout_desc = view.etLayout_desc_dialogBank
        builder.setView(view)
            .setTitle("Menambah pengeluaran atau pemasukan")
            .setPositiveButton("Tambah",null)
            .setNeutralButton("Batal",null)

        if(editAndDelete == true){
            builder.setNegativeButton("Hapus",null)
                .setPositiveButton("Update",null)
        }
        if(editAndDelete == true){
            et_nominal.setText(bankNominal.toString())
            et_desc.setText(bankDesc)
            when(bankType){
                "out"-> view.cb_pengeluaran_dialogBank.isChecked = true
                else -> view.cb_pengeluaran_dialogBank.isChecked = false
            }
        }

        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var type = "in"
            if(view.cb_pengeluaran_dialogBank.isChecked == true){
                type = "out"
            }
            validasiFromNominal(et_nominal,etLayout_nominal,et_desc,etLayout_desc,type)
            if(tambahBeres == true){
                alertDialog.dismiss()
                tambahBeres = false
            }
        }
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            if(editAndDelete == true){
                mBankViewModel.deletByID(bankID)
                editAndDelete = false
            }
            alertDialog.dismiss()
        }
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            alertDialog.cancel()
            editAndDelete = false
        }
    }

    override fun itemClickListener(bankEntity: BankEntity, position: Int) {
        bankID = bankEntity.bankID
        bankNominal = bankEntity.bankNominal
        bankDesc = bankEntity.bankDesc
        bankType = bankEntity.type
        mTimestamp = bankEntity.timestamp

        editAndDelete = true
        showDialogTambah()
    }
}