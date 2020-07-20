package com.sabil.diarytale.room.bank

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BankViewModel(app: Application): AndroidViewModel(app) {
    private var bankRepo: BankRepo = BankRepo(app)

    fun upsert(bankEntity: BankEntity){
        bankRepo.upsert(bankEntity)
    }

    fun clearHistory(){
        bankRepo.clearHistory()
    }

    fun deletByID(bankID: String){
        bankRepo.deleteByID(bankID)
    }

    fun getDataBankByType(type:String) = bankRepo.getDataBankByType(type)
    fun getTotalNominalIn() = bankRepo.getTotalNominalIn()
    fun getTotalNominalOut() = bankRepo.getTotalNominalOut()


}