package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myPlants")
class MyPlant (
    @PrimaryKey
    val id : Int,
    val platingDate: String,
    val sunlight: String,
    val watering : String,
    val fertilizingFreq: String,
    val disease: String
)
