package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey
    val id : Int,
    val name : String,
    val image : String,
    val indoor : Boolean,
    val edible : Boolean,
    val cycle : String,
    val sunlight: String,
    val location : String,
    val season : String,
    val watering : String



)
