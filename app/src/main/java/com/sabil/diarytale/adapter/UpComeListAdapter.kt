package com.sabil.diarytale.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.list.ListEntity
import kotlinx.android.synthetic.main.list_layout_rv.view.*
import java.util.*

class UpComeListAdapter: RecyclerView.Adapter<UpComeListAdapter.ViewHolder>() {

    private lateinit var todoListList:List<ListEntity>
    private lateinit var clickListener: ItemClickListener

    interface ItemClickListener{
        fun upcomeItemCLickListener(listEntity: ListEntity,cbCondition: Boolean)
        fun upcomeDeleteClick(listEntity: ListEntity)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTitle = view.tv_titleList_listLayout
        val tvWaktu = view.tv_waktuList_listLayout
        val cbDone = view.cb_listDone_listLayout

        fun bind(listEntity: ListEntity, clickListener: ItemClickListener){
            tvTitle.text = listEntity.listTitle
            val listCal = Calendar.getInstance()
            listCal.timeInMillis = listEntity.listTimestamp * 1000
            val listDate = listCal.get(Calendar.DATE)
            val currentDate = Calendar.getInstance().get(Calendar.DATE)
            if(listDate == currentDate){
                tvWaktu.text = DateFormat.format("HH:mm",listCal).toString()
            }
            else if(listEntity.allDay == true){
                tvWaktu.text = DateFormat.format("dd MMM yyyy",listCal).toString()
            }
            else{
                tvWaktu.text = DateFormat.format("dd MMM yyyy HH:mm",listCal).toString()
            }
            cbDone.isChecked = listEntity.listDone

            itemView.cb_listDone_listLayout.setOnClickListener {
                val cbCondition = itemView.cb_listDone_listLayout.isChecked
                clickListener.upcomeItemCLickListener(listEntity,cbCondition)
            }
            itemView.btn_deleteList_listLayout.setOnClickListener {
                clickListener.upcomeDeleteClick(listEntity)
            }
        }
    }

    fun upComeListAdapter(todoListList: List<ListEntity>,clickListener: ItemClickListener){
        this.todoListList = todoListList
        this.clickListener = clickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return todoListList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoListList.get(position),clickListener)
    }
}