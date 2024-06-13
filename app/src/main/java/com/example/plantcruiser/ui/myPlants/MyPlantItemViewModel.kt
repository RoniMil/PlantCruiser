package com.example.plantcruiser.ui.myPlants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlantItemViewModel @Inject constructor(
    private val plantRepository: Repository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _plant = _id.switchMap {
        plantRepository.getMyPlant(it)
    }

    val plant: LiveData<MyPlant> = _plant

    fun setId(id: Int) {
        _id.value = id
    }
}