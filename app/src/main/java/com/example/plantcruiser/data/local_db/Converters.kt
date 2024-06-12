package com.example.plantcruiser.data.local_db

import androidx.room.TypeConverter
import com.example.plantcruiser.data.models.DefaultImage
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

//    @TypeConverter
//    fun fromDefaultImageMap(defaultImageMap: Map<String, String>): String? {
//        return defaultImageMap["regular_url"]
//    }
//
//    @TypeConverter
//    fun toDefaultImageMap(regularUrl: String?): Map<String, String> {
//        val map = mutableMapOf<String, String>()
//        map["regular_url"] = "shimi"
////        if (regularUrl != null) {
////            map["regular_url"] = regularUrl
////        }
//        return map
//    }
    @TypeConverter
     fun fromDefaultImage(defaultImage : DefaultImage?) : String? {
        return defaultImage?.regular_url
    }

    @TypeConverter
    fun toDefaultImage(regular_url : String?) : DefaultImage {
        return DefaultImage(regular_url = regular_url)
    }

    @TypeConverter
    fun fromSunlightList(sunlightList: List<String>?): String? {
        if (sunlightList == null) {
            return null
        }
        return gson.toJson(sunlightList)
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

