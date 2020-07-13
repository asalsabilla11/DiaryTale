package com.sabil.diarytale.room.notes

import android.app.Application
import com.sabil.diarytale.room.DiaryTaleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NoteRepo(app: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private var noteDao: NoteDao?

    init {
        val db = DiaryTaleDB.getInstance(app)
        noteDao = db.noteDao
    }

    fun upsert(noteEntity: NoteEntity){
        launch {
            upsertBackground(noteEntity)
        }
    }

    fun getAllNote() = noteDao?.getAllNote()

    fun deleteAllNote() = noteDao?.deleteAllData()
    fun deletByID(noteID: String) = noteDao?.deleteByID(noteID)


    private suspend fun upsertBackground(noteEntity: NoteEntity){
        withContext(IO){
            noteDao?.upsert(noteEntity)
        }
    }

}