package com.sabil.diarytale.room.nutrition

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class NutritionViewModel(app: Application): AndroidViewModel(app) {

    private var repo = NutritionRepo(app)

    fun upsertNutrition(nutritionEntity: NutritionEntity)
            = repo.upsertNutrition(nutritionEntity)

    fun getNutrition(nutritionKategori: String)
            = repo.getNutrition(nutritionKategori)
}