package com.example.plantcruiser.data.remote_db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantRemoteDataSource @Inject constructor(
    private val plantService: PlantService) : BaseDataSource() {

    suspend fun getPlants(key: String, page : Int) = getResult { plantService.getPlants(key, page) }
    suspend fun getPlant(id : Int, key : String) = getResult { plantService.getPlant(id, key) }
}
