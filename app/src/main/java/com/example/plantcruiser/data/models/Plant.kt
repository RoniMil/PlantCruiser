package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey
    val id : Int,
    val common_name : String,
    val default_image: Images?,
    val cycle : String,
    val sunlight: List<String>,
    val watering : String

)



