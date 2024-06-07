package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.data.models.AllPlants
import com.example.plantcruiser.data.models.Plant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantService {

    // retrieve 30 plants from the page numbered page from the API
    @GET("/species-list?")
    suspend fun getPlants(@Query("key") key : String , @Query("page") page : Int) : Response<AllPlants>

    // retrieve a specific plant with the API database ID from the API
    @GET("/species/details/{id}?")
    suspend fun getPlant(@Path("id") id : Int, @Query("key") key : String) : Response<Plant>

}