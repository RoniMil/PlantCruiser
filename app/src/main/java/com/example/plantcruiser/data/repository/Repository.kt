package com.example.plantcruiser.data.repository

import com.example.plantcruiser.data.local_db.MyPlantDao
import com.example.plantcruiser.data.local_db.PlantDao
import com.example.plantcruiser.data.local_db.SuggestedPlantsDao
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.remote_db.PlantRemoteDataSource
import com.example.plantcruiser.utils.performFetching
import com.example.plantcruiser.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val plantRemoteDataSource: PlantRemoteDataSource,
    private val plantLocalDataSource: PlantDao,
    private val myPlantLocalDataSource: MyPlantDao,
    private val suggestedPlantLocalDataSource: SuggestedPlantsDao
) {
    fun getPlants(page: Int) = performFetchingAndSaving(
        { plantLocalDataSource.getPlants() },
        { plantRemoteDataSource.getPlants(page) },
        { plantLocalDataSource.insertPlants(it.data) }
    )

    fun getSuggestedPlants(options: Map<String, String?>) = performFetchingAndSaving(
        { suggestedPlantLocalDataSource.getPlants() },
        { plantRemoteDataSource.getSuggestedPlants(options) },
        { suggestedPlantLocalDataSource.insertPlants(it.data) }
    )

    fun getPlant(id: Int) = performFetching { plantLocalDataSource.getPlant(id) }

    fun getSuggestedPlant(id: Int) = performFetching { suggestedPlantLocalDataSource.getPlant(id) }

    fun getMyPlant(id: Int) = myPlantLocalDataSource.getMyPlant(id)
    fun getAllMyPlants() = myPlantLocalDataSource.getAllMyPlants()

    suspend fun insertPlant(plant: MyPlant) = myPlantLocalDataSource.insertPlant(plant)

    suspend fun updatePlant(plant: MyPlant) = myPlantLocalDataSource.updatePlant(plant)

    suspend fun deletePlant(plant: MyPlant) = myPlantLocalDataSource.deletePlant(plant)

    suspend fun deleteAllSuggestions() = suggestedPlantLocalDataSource.deleteAll()


}