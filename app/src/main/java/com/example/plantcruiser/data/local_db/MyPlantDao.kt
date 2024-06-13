package com.example.plantcruiser.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.models.Plant


@Dao
interface MyPlantDao {
    @Query("SELECT * FROM myPlants")
    fun getAllMyPlants() : LiveData<List<MyPlant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlant(plant: MyPlant)

    @Update
    fun updatePlant(plant: MyPlant)

    @Delete
    fun deletePlant(plant: MyPlant)
}