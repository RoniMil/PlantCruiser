package com.example.plantcruiser.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantcruiser.data.models.MyPlant

// Dao for the MyPlant type, allow CRUD actions on DB
@Dao
interface MyPlantDao {
    @Query("SELECT * FROM myPlants")
    fun getAllMyPlants() : LiveData<List<MyPlant>>

    @Query("SELECT * FROM myPlants WHERE id = :id")
    fun getMyPlant(id : Int) : LiveData<MyPlant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: MyPlant)

    @Update
    suspend fun updatePlant(plant: MyPlant)

    @Delete
    suspend fun deletePlant(plant: MyPlant)
}