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
    fun getPlants(page : Int) = performFetchingAndSaving(
        {plantLocalDataSource.getPlants()},
        {plantRemoteDataSource.getPlants(page)},
        {plantLocalDataSource.insertPlants(it.data)}
    )

    fun getPlant(id : Int) = performFetchingAndSaving(
        {plantLocalDataSource.getPlant(id)},
        {plantRemoteDataSource.getPlant(id)},
        {plantLocalDataSource.insertPlant(it)}
    )

    fun getDiseases(page : Int) = performFetchingAndSaving(
        {diseaseLocalDataSource.getDiseases()},
        {diseaseRemoteDataSource.getDiseases(page)},
        {diseaseLocalDataSource.insertDiseases(it.data)}
    )

    fun getDisease(id : Int) = diseaseLocalDataSource.getDisease(id)

}