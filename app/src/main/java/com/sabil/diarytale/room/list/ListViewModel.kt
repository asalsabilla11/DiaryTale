package com.sabil.diarytale.room.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ListViewModel(app: Application): AndroidViewModel(app) {

    private var repo: ListRepo = ListRepo(app)

    fun listUpsert(listEntity: ListEntity) = repo.listUpsert(listEntity)

    fun updateDone(newListDone: Boolean, listID: String) = repo.updateDone(newListDone,listID)

    fun listDeleteAll() = repo.deleteAll()
    fun listDeleteAllDone() = repo.listDeleteAllDone()
    fun listDeleteOne(listID: String) = repo.deleteOne(listID)

    fun getAllList() = repo.getAllList()
}