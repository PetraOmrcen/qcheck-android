package com.example.myapplication.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MapViewModel(
        private val repository: PlaceRepository
) : BaseViewModel(repository) {

    private val _places: MutableLiveData<Resource<List<Place>>> = MutableLiveData()
    val places: LiveData<Resource<List<Place>>>
        get() = _places

    fun getPlaces() = viewModelScope.launch {
        _places.value = Resource.Loading
        _places.value = repository.getPlaces()
    }

    suspend fun saveUserLocation(location: Location) {
        repository.saveUserLocation(location)
    }
}