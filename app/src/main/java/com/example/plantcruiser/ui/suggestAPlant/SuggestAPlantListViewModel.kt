package com.example.plantcruiser.ui.suggestAPlant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.plantcruiser.data.models.SuggestedPlant
import com.example.plantcruiser.data.repository.Repository
import com.example.plantcruiser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestAPlantListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _options = MutableLiveData<Map<String, String?>>()


    private val _plants = _options.switchMap {
        repository.getSuggestedPlants(it)
    }

    val plants: LiveData<Resource<List<SuggestedPlant>>> get() = _plants

    fun setOptions(indoor: String?, sunlight: String?, hardiness: String?) {
        val map = mutableMapOf<String, String?>()
        map["indoor"] = indoor
        map["sunlight"] = sunlight
        map["hardiness"] = hardiness
        _options.value = map
    }

    fun deleteSuggestions() = viewModelScope.launch { repository.deleteAllSuggestions() }


}