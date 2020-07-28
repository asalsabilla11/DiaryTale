package com.sabil.diarytale.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sabil.diarytale.R
import com.sabil.diarytale.adapter.KatNutrisiAdapter
import com.sabil.diarytale.room.nutrition.NutritionEntity
import com.sabil.diarytale.room.nutrition.NutritionViewModel
import kotlinx.android.synthetic.main.activity_nutrition.*
import java.util.*
import kotlin.collections.ArrayList

class NutritionActivity : AppCompatActivity(), KatNutrisiAdapter.ItemClickListener {


    private lateinit var nutrisiViewModel: NutritionViewModel

    override fun itemClickListener(kategoriNutrisi: KategoriNutrisi) {
        val intent = Intent(this,NutritionViewActivity::class.java)
        intent.putExtra("KAT_NUTRISI",kategoriNutrisi.namaKategori)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        nutrisiViewModel = ViewModelProviders.of(this).get(NutritionViewModel::class.java)

        setSupportActionBar(toolbar_nutririon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setKategoriNutrisi()

        nutrisiViewModel.getNutrition("Vitamin").observe(this,
            Observer<List<NutritionEntity>>{
                println("Data => $it")
            })
    }

    private fun setKategoriNutrisi() {
        val katNutrisiList = ArrayList<KategoriNutrisi>()
        katNutrisiList.add(
            KategoriNutrisi(
                "Calcium",
                R.drawable.salmon
            )
        )
        katNutrisiList.add(
            KategoriNutrisi(
                "Karbohidrat",
                R.drawable.sweet_potato
            )
        )
        katNutrisiList.add(
            KategoriNutrisi(
                "Dairy",
                R.drawable.yogurt
            )
        )
        katNutrisiList.add(
            KategoriNutrisi(
                "Protein",
                R.drawable.beef
            )
        )
        katNutrisiList.add(
            KategoriNutrisi(
                "Vitamin",
                R.drawable.berries
            )
        )

        val katNutAdapter = KatNutrisiAdapter()
        katNutAdapter.katNutrisiAdapter(katNutrisiList, this)
        rv_kategoriNutrisi_nutrision.layoutManager = GridLayoutManager(this, 2)
        rv_kategoriNutrisi_nutrision.adapter = katNutAdapter
    }

    data class KategoriNutrisi(
        val namaKategori: String,
        val imageKategori: Int
    )
}