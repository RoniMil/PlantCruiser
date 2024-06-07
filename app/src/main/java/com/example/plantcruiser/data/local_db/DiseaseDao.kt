package com.example.plantcruiser.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantcruiser.data.models.Disease

@Dao
interface DiseaseDao {

    @Query("SELECT * FROM diseases")
    fun getDiseases() : LiveData<List<Disease>>

    @Query("SELECT * FROM diseases WHERE id = :id")
    fun getDisease(id : Int) : LiveData<Disease>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiseases(diseases : List<Disease>)
}