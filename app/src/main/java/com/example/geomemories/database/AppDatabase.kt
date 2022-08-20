package com.example.geomemories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    // Singleton. Allows for reference to a single AppDatabase instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Thread safe access to 'this' which refers to AppDatabase during initialization
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}