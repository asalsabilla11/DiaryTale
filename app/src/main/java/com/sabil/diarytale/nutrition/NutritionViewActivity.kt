package com.sabil.diarytale.nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sabil.diarytale.R
import com.sabil.diarytale.adapter.NutrisiAdapter
import com.sabil.diarytale.room.nutrition.NutritionEntity
import com.sabil.diarytale.room.nutrition.NutritionViewModel
import kotlinx.android.synthetic.main.activity_nutrition.*
import kotlinx.android.synthetic.main.activity_nutrition_view.*

class NutritionViewActivity : AppCompatActivity() {

    private lateinit var nutritionViewModel: NutritionViewModel
    private lateinit var nutritionAdapter: NutrisiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_view)

        nutritionViewModel = ViewModelProviders.of(this).get(NutritionViewModel::class.java)
        nutritionAdapter = NutrisiAdapter()

        val kategoriNutrisi = intent.getStringExtra("KAT_NUTRISI")

        setSupportActionBar(toolbar_nutritionView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(kategoriNutrisi)

        nutritionViewModel.getNutrition(kategoriNutrisi!!).observe(this,
            Observer<List<NutritionEntity>>{
                nutritionAdapter.nutrisiAdapter(it)
                rv_nutrition_nutritionView.layoutManager = GridLayoutManager(this,2)
                rv_nutrition_nutritionView.adapter = nutritionAdapter
            })
    }
}