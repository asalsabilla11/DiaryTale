package com.sabil.diarytale.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.bank.BankEntity
import kotlinx.android.synthetic.main.bank_layout_rv.view.*
import java.util.*

class BankAdapter:RecyclerView.Adapter<BankAdapter.ViewHolder>() {

    private lateinit var bankList: List<BankEntity>
    private lateinit var clickListener: ItemClickListener

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_bankDesc = view.tv_bankDesc_bankLayout
        val tv_bankNominal = view.tv_bankNominal_bankLayout
        val tv_timestamp = view.tv_timestamp_bankLayout
        val tv_type = view.tv_type_bankLayout

        fun bind(bankEntity: BankEntity,clickListener: ItemClickListener,position: Int){
            tv_bankDesc.text = bankEntity.bankDesc
            tv_bankNominal.text = String.format("%,d",bankEntity.bankNominal)
            val timestamp = Calendar.getInstance()
            timestamp.timeInMillis = bankEntity.timestamp * 1000
            tv_timestamp.text = DateFormat.format("d MMM yyyy",timestamp).toString()
            val type: String
            if(bankEntity.type == "in"){
                type = "Incoming"
            }else type = "Outgoing"
            tv_type.text = type

            itemView.setOnClickListener {
                clickListener.itemClickListener(bankEntity,position)
            }

        }
    }

    fun bankAdapter(bankList: List<BankEntity>, clickListener: ItemClickListener){
        this.bankList = bankList
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bank_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return bankList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bankList.get(position),clickListener,position)
    }

    interface ItemClickListener{
        fun itemClickListener(bankEntity: BankEntity, position: Int)
    }
}