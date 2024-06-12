package com.example.plantcruiser.network

import com.example.plantcruiser.data.models.PlantList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("https://perenual.com/api/species-list?key=sk-3qAU6662d02ceec3f5838&page=69")
    fun getYourData(): Call<PlantList>
}
