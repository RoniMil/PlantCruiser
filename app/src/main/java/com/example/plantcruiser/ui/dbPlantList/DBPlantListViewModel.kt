package com.example.plantcruiser.ui.dbPlantList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantcruiser.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DBPlantListViewModel @Inject constructor(repository : Repository, private val currentPage: MutableLiveData<Int>) : ViewModel() {

    val currentPlantDBPage: LiveData<Int> get() = currentPage

    fun updateGlobalVariable(newValue: Int) {
        currentPage.value = newValue
    }

    val plants = repository.getPlants(currentPage.value!!)
}