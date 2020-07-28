package com.sabil.diarytale.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sabil.diarytale.room.alarm.AlarmDao
import com.sabil.diarytale.room.alarm.AlarmEntity
import com.sabil.diarytale.room.bank.BankDao
import com.sabil.diarytale.room.bank.BankEntity
import com.sabil.diarytale.room.drink.DrinkDao
import com.sabil.diarytale.room.drink.DrinkEntity
import com.sabil.diarytale.room.list.ListDao
import com.sabil.diarytale.room.list.ListEntity
import com.sabil.diarytale.room.notes.NoteDao
import com.sabil.diarytale.room.notes.NoteEntity
import com.sabil.diarytale.room.nutrition.NutritionDao
import com.sabil.diarytale.room.nutrition.NutritionEntity


@Database(entities = [NoteEntity::class, BankEntity::class, AlarmEntity::class,
    ListEntity::class, DrinkEntity::class, NutritionEntity::class],version = 10)
abstract class DiaryTaleDB: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val bankDao: BankDao
    abstract val alarmDao: AlarmDao
    abstract val listDao: ListDao
    abstract val drinkDao: DrinkDao
    abstract val nutritionDao: NutritionDao

    companion object{
        @Volatile
        private var INSTANCE: DiaryTaleDB? = null
        fun getInstance(context: Context): DiaryTaleDB{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryTaleDB::class.java,"diaryTaleDB"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}