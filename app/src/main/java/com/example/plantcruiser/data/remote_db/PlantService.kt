package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.data.models.PlantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantService {

    @GET("species-list?")
    suspend fun getPlants(@Query("key") key : String , @Query("page") page : Int) : Response<PlantList>

}