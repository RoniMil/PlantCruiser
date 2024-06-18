package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.data.models.PlantList
import com.example.plantcruiser.data.models.SuggestedPlantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

// Retrofit service interface fo making HTTP requests to the API
interface PlantService {

    @GET("species-list?")
    suspend fun getPlants(@Query("key") key : String ,@Query("page") page : Int) : Response<PlantList>

    @GET("species-list?")
    suspend fun getSuggestedPlants(@Query("key") key : String ,@QueryMap options: Map<String, String?>): Response<SuggestedPlantList>

}