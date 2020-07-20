package com.sabil.diarytale.room.bank

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BankDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bankEntity: BankEntity)

    @Query("select * from bank where type = :type order by timestamp asc")
    fun getDataBankByType(type: String):LiveData<List<BankEntity>>

    @Query("select sum(bankNominal) from bank where type = 'in'")
    fun getTotalNominalIn():LiveData<Int>

    @Query("select sum(bankNominal) from bank where type = 'out'")
    fun getTotalNominalOut():LiveData<Int>

    @Query("delete from bank")
    fun clearHistory()

    @Query("delete from bank where bankID = :bankID")
    fun deleteByID(bankID: String)
}