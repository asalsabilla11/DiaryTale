package com.sabil.diarytale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.nutrition.NutritionActivity
import com.sabil.diarytale.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.katnutrisi_layout_rv.view.*

class KatNutrisiAdapter: RecyclerView.Adapter<KatNutrisiAdapter.ViewHolder>() {

    private lateinit var listKatNutrisi: List<NutritionActivity.KategoriNutrisi>
    private lateinit var clickListener: ItemClickListener

    interface ItemClickListener{
        fun itemClickListener(kategoriNutrisi: NutritionActivity.KategoriNutrisi)
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val tvNama = view.namaKategori_katNusLayout
        val ivImage = view.imageKategori_katNusLayout

        fun bind(kategoriNutrisi: NutritionActivity.KategoriNutrisi, clickListener: ItemClickListener){
            tvNama.text = kategoriNutrisi.namaKategori
            Picasso.get().load(kategoriNutrisi.imageKategori).fit().into(ivImage)

            itemView.setOnClickListener {
                clickListener.itemClickListener(kategoriNutrisi)
            }
        }
    }

    fun katNutrisiAdapter(listKatNutrisi: List<NutritionActivity.KategoriNutrisi>, clickListener: ItemClickListener){
        this.listKatNutrisi = listKatNutrisi
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.katnutrisi_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return listKatNutrisi.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listKatNutrisi.get(position),clickListener)
    }
}