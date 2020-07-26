package com.sabil.diarytale.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.drink.DrinkEntity
import kotlinx.android.synthetic.main.drink_layout_rv.view.*
import java.util.*

class DrinkAdapter: RecyclerView.Adapter<DrinkAdapter.ViewHolder>() {

    private lateinit var drinkList: List<DrinkEntity>

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTanggal = view.tv_tanggal_drinkLayout
        val tvBanyakMinum = view.tv_banyakMinum_drink

        fun bind(drinkEntity: DrinkEntity){
            tvBanyakMinum.text = drinkEntity.howMuchDrink.toString()
            val tanggal = Calendar.getInstance()
            tanggal.timeInMillis = drinkEntity.drinkTimestamp * 1000
            tvTanggal.text = DateFormat.format("dd MMM yyyy",tanggal).toString()
        }

    }
    fun drinkAdapter(drinkList: List<DrinkEntity>){
        this.drinkList = drinkList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.drink_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drinkList.get(position))
    }
}