package com.example.plantcruiser.data.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myPlants")
class MyPlant (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val image: Bitmap?,
    val plantingDate: String,
    val sunlight: String,
    val watering : String,
    val fertilizingFreq: String,
    val disease: String
)
