package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diseases")
data class Disease(
    @PrimaryKey
    val id : Int,
    val name : String,
    val image : String,
    val host : String,
    val description : String,
    val solution : String
)