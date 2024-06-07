package com.example.plantcruiser.data.repository

import com.example.plantcruiser.data.local_db.DiseaseDao
import com.example.plantcruiser.data.local_db.PlantDao
import com.example.plantcruiser.data.remote_db.DiseaseRemoteDataSource
import com.example.plantcruiser.data.remote_db.PlantRemoteDataSource
import com.example.plantcruiser.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val plantRemoteDataSource : PlantRemoteDataSource,
    private val plantLocalDataSource : PlantDao,
    private val diseaseRemoteDataSource : DiseaseRemoteDataSource,
    private val diseaseLocalDataSource : DiseaseDao


){
    fun getPlants(key : String, page : Int) = performFetchingAndSaving(
        {plantLocalDataSource.getPlants()},
        {plantRemoteDataSource.getPlants(key, page)},
        {plantLocalDataSource.insertPlants(it.results)}
    )

    fun getPlant(id : Int, key : String) = performFetchingAndSaving(
        {plantLocalDataSource.getPlant(id)},
        {plantRemoteDataSource.getPlant(id, key)},
        {plantLocalDataSource.insertPlant(it)}
    )

    fun getDiseases(key : String, page : Int) = performFetchingAndSaving(
        {diseaseLocalDataSource.getDiseases()},
        {diseaseRemoteDataSource.getDiseases(key, page)},
        {diseaseLocalDataSource.insertDiseases(it.results)}
    )

    fun getDisease(id : Int, key : String) = diseaseLocalDataSource.getDisease(id)

}