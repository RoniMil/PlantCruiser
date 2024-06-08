package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseaseRemoteDataSource @Inject constructor(
    private val diseaseService: DiseaseService) : BaseDataSource() {

    suspend fun getDiseases(page: Int) = getResult { diseaseService.getDiseases(Constants.API_KEY, page) }
}