package com.example.plantcruiser.ui.dbPlantList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.data.repository.Repository
import com.example.plantcruiser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DBPlantListViewModel @Inject constructor(repository : Repository, private val currentPage: MutableLiveData<Int>) : ViewModel() {

    val currentPlantDBPage: LiveData<Int> get() = currentPage

    private val _plants = MediatorLiveData<Resource<List<Plant>>>()
    val plants: LiveData<Resource<List<Plant>>> get() = _plants

    init {
        _plants.addSource(currentPage) { page ->
            repository.getPlants(page).observeForever { resource ->
                _plants.value = resource
            }
        }
    }

    fun updateGlobalVariable(newValue: Int) {
        currentPage.value = newValue
    }

}