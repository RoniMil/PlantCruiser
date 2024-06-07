package com.example.plantcruiser.data.remote_db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseaseRemoteDataSource @Inject constructor(
    private val diseaseService: DiseaseService) : BaseDataSource() {

    suspend fun getDiseases(key: String, page: Int) = getResult { diseaseService.getDiseases(key, page) }
}