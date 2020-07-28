package com.sabil.diarytale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.nutrition.NutritionEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.katnutrisi_layout_rv.view.*

class NutrisiAdapter: RecyclerView.Adapter<NutrisiAdapter.ViewHolder>() {

    private lateinit var listNutrisi: List<NutritionEntity>

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvNama = view.namaKategori_katNusLayout
        val ivImage = view.imageKategori_katNusLayout

        fun bind(nutritionEntity: NutritionEntity){
            tvNama.text = nutritionEntity.nutrisiNama
            Picasso.get().load(nutritionEntity.nutrisiImage).fit().into(ivImage)
        }
    }

    fun nutrisiAdapter(listNutrisi: List<NutritionEntity>){
        this.listNutrisi = listNutrisi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.katnutrisi_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return listNutrisi.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNutrisi.get(position))
    }

}