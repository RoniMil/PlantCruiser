package com.example.plantcruiser.ui.myPlants

import androidx.lifecycle.ViewModel
import com.example.plantcruiser.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// viewModel for the plant list myplants fragment
@HiltViewModel
class MyPlantsViewModel @Inject constructor(repository : Repository) : ViewModel() {
    val plants = repository.getAllMyPlants()
}