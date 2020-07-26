package com.sabil.diarytale.room.drink

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class DrinkViewModel(app: Application): AndroidViewModel(app) {

    private var repo: DrinkRepo = DrinkRepo(app)


    fun drinkUpsert(drinkEntity: DrinkEntity) = repo.drinkUpster(drinkEntity)

    fun drinkDeleteAll() = repo.drinkDeleteAll()
    fun drinkDeleteByID(drinkID: String) = repo.drinkDeleteByID(drinkID)

    fun drinkGetAll() = repo.drinkGetAll()

}