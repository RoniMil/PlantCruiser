package com.example.plantcruiser.utils

import com.example.plantcruiser.BuildConfig

// class for holding constants for the app
class Constants {
    companion object {
        const val BASE_URL = "https://perenual.com/api/"
        const val API_KEY = BuildConfig.API_KEY
        // for not exceeding daily api calls
        const val MAX_PLANT_PAGE = 50

    }
}