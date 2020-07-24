package com.sabil.diarytale.room.list

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ListRepo(app: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private var listDao: ListDao

    init {
        val db = DiaryTaleDB.getInstance(app)
        listDao = db.listDao
    }


    fun listUpsert(listEntity: ListEntity){
        launch {
            listUpsertBG(listEntity)
        }
    }
    private suspend fun listUpsertBG(listEntity: ListEntity){
        withContext(IO){
            listDao.listUpSert(listEntity)
        }
    }

    fun updateDone(newListDone: Boolean, listID: String){
        launch {
            updateDoneBG(newListDone,listID)
        }
    }
    private suspend fun updateDoneBG(newListDone: Boolean, listID: String){
        withContext(IO){
            listDao.updateDone(newListDone,listID)
        }
    }

    fun deleteAll(){
        launch {
            deleteAllBG()
        }
    }
    private suspend fun deleteAllBG(){
        withContext(IO){
            listDao.deleteAll()
        }
    }
    fun deleteOne(listID: String){
        launch {
            deleteOneBG(listID)
        }
    }
    private suspend fun deleteOneBG(listID: String){
        withContext(IO){
            listDao.deleteOne(listID)
        }
    }
    fun listDeleteAllDone(){
        launch {
            listDeleteAllDoneBG()
        }
    }
    private suspend fun listDeleteAllDoneBG(){
        withContext(IO){
            listDao.listDeleteAllDone()
        }
    }

    fun getAllList() = listDao.getAllList()

}