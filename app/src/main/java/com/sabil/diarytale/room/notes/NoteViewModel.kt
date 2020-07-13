package com.sabil.diarytale.room.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class NoteViewModel(app: Application): AndroidViewModel(app) {

    private var noteRepo: NoteRepo

    init {
        noteRepo = NoteRepo(app)
    }

    fun upset(noteEntity: NoteEntity) = noteRepo.upsert(noteEntity)

    fun getAllNote() = noteRepo.getAllNote()

    fun deleteByID(noteID: String) = noteRepo.deletByID(noteID)
    fun deletAllNote() = noteRepo.getAllNote()

}