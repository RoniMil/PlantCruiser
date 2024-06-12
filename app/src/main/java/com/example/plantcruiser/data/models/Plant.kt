package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey
    val id : Int,
    val common_name : String,
    val default_image: DefaultImage?,
    val cycle : String,
    val sunlight: List<String>,
    val watering : String

)

data class DefaultImage(
    val regular_url : String?
)

