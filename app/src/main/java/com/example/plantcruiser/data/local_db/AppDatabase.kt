package com.example.plantcruiser.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.data.models.SuggestedPlant

// Database class
@Database(entities = [Plant::class, MyPlant::class, SuggestedPlant::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Abstract functions for all database's dao's
    abstract fun plantDao(): PlantDao

    abstract fun myPlantsDao() : MyPlantDao

    abstract fun suggestedPlantsDao() : SuggestedPlantsDao

    // Companion object for creating the app's database
    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDatabase"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
    }
}
