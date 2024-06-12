package com.example.plantcruiser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diseases")
data class Disease(
    @PrimaryKey
    val id : Int,
    val common_name : String,
    val images : Images?,
    val host : List<String>,
)

