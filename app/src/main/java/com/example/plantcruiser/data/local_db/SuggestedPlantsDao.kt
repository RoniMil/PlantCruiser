package com.example.plantcruiser.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantcruiser.data.models.SuggestedPlant

@Dao
interface SuggestedPlantsDao {

    @Query("SELECT * FROM suggestedPlants")
    fun getPlants() : LiveData<List<SuggestedPlant>>

    @Query("SELECT * FROM suggestedPlants WHERE id = :id")
    fun getPlant(id : Int) : LiveData<SuggestedPlant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: SuggestedPlant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(plants : List<SuggestedPlant>)
}