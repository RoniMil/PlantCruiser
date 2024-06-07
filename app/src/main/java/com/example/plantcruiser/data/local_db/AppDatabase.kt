package com.example.plantcruiser.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantcruiser.data.models.Disease
import com.example.plantcruiser.data.models.Plant


@Database(entities = [Plant::class, Disease::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

    abstract fun diseaseDao(): DiseaseDao

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
