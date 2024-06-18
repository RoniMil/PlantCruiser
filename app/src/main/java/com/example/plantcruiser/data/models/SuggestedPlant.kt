package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Model for Suggest A Plant feature items
@Entity(tableName = "suggestedPlants")
data class SuggestedPlant(
    @PrimaryKey
    val id : Int,
    val common_name : String,
    val default_image: Images?,
    val cycle : String,
    val sunlight: List<String>,
    val watering : String

)





