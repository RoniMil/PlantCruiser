package com.example.plantcruiser.ui.myPlantsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantcruiser.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DBPlantListViewModel @Inject constructor(repository : Repository) : ViewModel() {
    val plants = repository.getAllMyPlants()
}