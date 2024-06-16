package com.example.plantcruiser.di

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.plantcruiser.data.local_db.AppDatabase
import com.example.plantcruiser.data.remote_db.PlantService
import com.example.plantcruiser.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrentPage(): MutableLiveData<Int> {
        return MutableLiveData<Int>().apply { value = 1 }
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun providePlantService(retrofit: Retrofit): PlantService =
        retrofit.create(PlantService::class.java)

    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun providePlantDao(database: AppDatabase) = database.plantDao()

    @Provides
    @Singleton
    fun provideMyPlantsDao(database: AppDatabase) = database.myPlantsDao()

    @Provides
    @Singleton
    fun provideSuggestedPlantsDao(database: AppDatabase) = database.suggestedPlantsDao()

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }


}
