package com.example.geomemories

import android.app.Application
import com.example.geomemories.database.AppDatabase

class GeoMemoriesApplication: Application() {
    // Create/get reference to the database before Activities are created
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}