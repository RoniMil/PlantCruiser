package com.example.plantcruiser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//initializes Hilt as the dependency injection framework for the Android application
@HiltAndroidApp
class MyApplication : Application() {

}