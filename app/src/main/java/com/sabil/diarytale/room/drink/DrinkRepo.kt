package com.sabil.diarytale.room.drink

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class DrinkRepo(app: Application):CoroutineScope {

    private var drinkDao: DrinkDao

    override val coroutineContext: CoroutineContext
        get() = IO

    init {
        val db = DiaryTaleDB.getInstance(app)
        drinkDao = db.drinkDao
    }

    fun drinkUpster(drinkEntity: DrinkEntity){
        launch {
            drinkUpsterBG(drinkEntity)
        }
    }
    private suspend fun drinkUpsterBG(drinkEntity: DrinkEntity){
        withContext(IO){
            drinkDao.drinkUpsert(drinkEntity)
        }
    }

    fun drinkDeleteAll(){
        launch {
            drinkDeleteAllBG()
        }
    }
    private suspend fun drinkDeleteAllBG(){
        withContext(IO){
            drinkDao.drinkDeleteALl()
        }
    }

    fun drinkDeleteByID(drinkID: String){
        launch {
            drinkDeleteByIDBG(drinkID)
        }
    }
    private suspend fun drinkDeleteByIDBG(drinkID: String){
        withContext(IO){
            drinkDao.drinkDeleteByID(drinkID)
        }
    }

    fun drinkGetAll() = drinkDao.drinkGetAll()
}