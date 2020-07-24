package com.sabil.diarytale.room.list

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ListDao {

    @Insert
    fun listUpSert(listEntity: ListEntity)

    @Query("delete from list where listID = :listID")
    fun deleteOne(listID: String)
    @Query("delete from list")
    fun deleteAll()
    @Query("delete from list where listDone = 1")
    fun listDeleteAllDone()

    @Query("select * from list order by listDone asc, listTimestamp asc ")
    fun getAllList(): LiveData<List<ListEntity>>

    @Query("update list set listDone = :newListDone where listID = :listID")
    fun updateDone(newListDone: Boolean,listID: String)

}