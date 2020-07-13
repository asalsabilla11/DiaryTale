package com.sabil.diarytale.room.notes

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sabil.diarytale.room.notes.NoteEntity


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(noteEntity: NoteEntity)

    @Query("delete from notes where noteID = :noteID")
    suspend fun deleteByID(noteID: String)
    @Query("delete from notes")
    fun deleteAllData()

    @Query("select * from notes order by timestamp")
    fun getAllNote(): LiveData<List<NoteEntity>>
}