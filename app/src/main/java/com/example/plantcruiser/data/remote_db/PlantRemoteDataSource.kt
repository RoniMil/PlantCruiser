package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantRemoteDataSource @Inject constructor(
    private val plantService: PlantService) : BaseDataSource() {

    suspend fun getPlants(page : Int) = getResult { plantService.getPlants(Constants.API_KEY, page) }
    suspend fun getPlant(id : Int) = getResult { plantService.getPlant(id, Constants.API_KEY) }
}
