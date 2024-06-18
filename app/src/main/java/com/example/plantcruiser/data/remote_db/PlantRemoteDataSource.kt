package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

// Class that serves as a remote data source for fetching plant data from the API.
@Singleton
class PlantRemoteDataSource @Inject constructor(
    private val plantService: PlantService
) : BaseDataSource() {

    suspend fun getPlants(page: Int) = getResult { plantService.getPlants(Constants.API_KEY, page) }

    suspend fun getSuggestedPlants(options: Map<String, String?>) =
        getResult { plantService.getSuggestedPlants(Constants.API_KEY, options) }
}
