package com.example.geomemories.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Using UNIX timestamp
    @ColumnInfo(name = "date")
    val date: Int,

    @ColumnInfo(name = "notes")
    val notes: String,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    // Image path on S3 bucket
    @Nullable @ColumnInfo(name = "image")
    val image: String?
)