package com.sabil.diarytale.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sabil.diarytale.room.notes.NoteDao
import com.sabil.diarytale.room.notes.NoteEntity


@Database(entities = [NoteEntity::class],version = 1)
abstract class DiaryTaleDB: RoomDatabase() {

    abstract val noteDao: NoteDao

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