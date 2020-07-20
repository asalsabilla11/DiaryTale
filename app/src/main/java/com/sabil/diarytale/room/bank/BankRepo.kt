package com.sabil.diarytale.room.bank

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BankRepo(app: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private var bankDao: BankDao

    init {
        val db = DiaryTaleDB.getInstance(app)
        bankDao = db.bankDao
    }

    fun upsert(bankEntity: BankEntity){
        launch {
            upsertBG(bankEntity)
        }
    }
    private suspend fun upsertBG(bankEntity: BankEntity) {
        withContext(IO) {
            bankDao.upsert(bankEntity)
        }
    }

    fun clearHistory(){
        launch {
            clearHistoryBG()
        }
    }

    private suspend fun clearHistoryBG(){
        withContext(IO){
            bankDao.clearHistory()
        }
    }

    fun deleteByID(bankID:String){
        launch {
            deleteByIDBG(bankID)
        }
    }
    private suspend fun deleteByIDBG(bankID: String){
        withContext(IO){
            bankDao.deleteByID(bankID)
        }
    }

    fun getDataBankByType(type:String) = bankDao.getDataBankByType(type)
    fun getTotalNominalIn() = bankDao.getTotalNominalIn()
    fun getTotalNominalOut() = bankDao.getTotalNominalOut()
}