package com.example.plantcruiser.data.models

// Model for Plant DB feature item list
data class PlantList(
    val data : List<Plant>,
    val current_page : Int
)
