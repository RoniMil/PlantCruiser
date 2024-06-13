package com.example.plantcruiser.data.repository

import com.example.plantcruiser.data.local_db.DiseaseDao
import com.example.plantcruiser.data.local_db.MyPlantDao
import com.example.plantcruiser.data.local_db.PlantDao
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.remote_db.DiseaseRemoteDataSource
import com.example.plantcruiser.data.remote_db.PlantRemoteDataSource
import com.example.plantcruiser.utils.performFetchingAndSaving
import com.example.plantcruiser.utils.performFetching
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val plantRemoteDataSource : PlantRemoteDataSource,
    private val plantLocalDataSource : PlantDao,
    private val diseaseRemoteDataSource : DiseaseRemoteDataSource,
    private val diseaseLocalDataSource : DiseaseDao,
    private val myPlantLocalDataSource: MyPlantDao
){
    fun getPlants(page : Int) = performFetchingAndSaving(
        {plantLocalDataSource.getPlants()},
        {plantRemoteDataSource.getPlants(page)},
        {plantLocalDataSource.insertPlants(it.data)}
    )

    fun getPlant(id : Int) = performFetching { plantLocalDataSource.getPlant(id) }

    fun getDiseases(page : Int) = performFetchingAndSaving(
        {diseaseLocalDataSource.getDiseases()},
        {diseaseRemoteDataSource.getDiseases(page)},
        {diseaseLocalDataSource.insertDiseases(it.data)}
    )

    fun getDisease(id : Int) = diseaseLocalDataSource.getDisease(id)

    fun getMyPlant(id: Int) = myPlantLocalDataSource.getMyPlant(id)
    fun getAllMyPlants() = myPlantLocalDataSource.getAllMyPlants()

    fun insertPlant(plant: MyPlant) = myPlantLocalDataSource.insertPlant(plant)

    fun updatePlant(plant: MyPlant) = myPlantLocalDataSource.updatePlant(plant)

    fun deletePlant(plant: MyPlant) = myPlantLocalDataSource.deletePlant(plant)

}