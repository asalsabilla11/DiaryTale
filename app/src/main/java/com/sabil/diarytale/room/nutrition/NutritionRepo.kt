package com.sabil.diarytale.room.nutrition

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NutritionRepo(app: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private var nutrisiDao: NutritionDao

    init {
        val db = DiaryTaleDB.getInstance(app)
        nutrisiDao = db.nutritionDao
    }


    fun upsertNutrition(nutritionEntity: NutritionEntity){
        launch {
            upsertNutritionBG(nutritionEntity)
        }
    }
    private suspend fun upsertNutritionBG(nutritionEntity: NutritionEntity){
        withContext(IO){
            nutrisiDao.upsert(nutritionEntity)
        }
    }

    fun getNutrition(nutrisiKategori: String)
            = nutrisiDao.getNutrition(nutrisiKategori)

}