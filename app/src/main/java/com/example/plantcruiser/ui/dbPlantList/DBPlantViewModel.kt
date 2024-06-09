package com.example.plantcruiser.ui.dbPlantList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.data.repository.Repository
import com.example.plantcruiser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DBPlantViewModel @Inject constructor(
    private val plantRepository: Repository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _plant = _id.switchMap {
        plantRepository.getPlant(it)
    }

    val plant: LiveData<Resource<Plant>> = _plant

    fun setId(id: Int) {
        _id.value = id
    }

}