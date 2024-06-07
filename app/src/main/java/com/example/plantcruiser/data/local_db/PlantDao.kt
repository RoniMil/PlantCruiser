package com.example.plantcruiser.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantcruiser.data.models.Plant

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants")
    fun getPlants() : LiveData<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :id")
    fun getPlant(id : Int) : LiveData<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(plants : List<Plant>)
}