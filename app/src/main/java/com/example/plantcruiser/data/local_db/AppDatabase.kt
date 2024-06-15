package com.example.plantcruiser.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.models.Plant


@Database(entities = [Plant::class, MyPlant::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

    abstract fun myPlantsDao() : MyPlantDao

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
