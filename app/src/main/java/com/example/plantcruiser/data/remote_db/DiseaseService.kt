package com.example.plantcruiser.data.remote_db

import com.example.plantcruiser.data.models.DiseaseList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiseaseService {
    // retrieve 30 plants from the page numbered page from the API
    @GET("pest-disease-list?")
    suspend fun getDiseases(@Query("key") key : String , @Query("page") page : Int) : Response<DiseaseList>

}