package com.example.plantcruiser.data.local_db

import androidx.room.TypeConverter
import com.example.plantcruiser.data.models.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromImages(images : Images?) : String? {
        return images?.regular_url
    }

    @TypeConverter
    fun toImages(regular_url : String?) : Images {
        return Images(regular_url = regular_url)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        return gson.toJson(list)
    }

    @TypeConverter
    fun toSunlightList(data: String?): List<String>? {
        if (data == null) {
            return null
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}

