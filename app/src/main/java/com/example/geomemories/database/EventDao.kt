package com.example.geomemories.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun addEvent(event: Event)

    // Flow is a coroutine, so suspend keyword not required
    @Query("SELECT * FROM event ORDER BY date DESC")
    fun getEvents(): Flow<List<Event>>

}