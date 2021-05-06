package com.example.myapplication.ui.search

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchViewModel(
        private val repository: PlaceRepository
) : BaseViewModel(repository) {

    private val _places: MutableLiveData<Resource<List<Place>>> = MutableLiveData()
    val places: LiveData<Resource<List<Place>>>
        get() = _places

    fun getPlaces() = viewModelScope.launch {
        _places.value = Resource.Loading
        _places.value = repository.getPlaces()
    }

    fun getDistance(place: Place): Float {
        val loc = Location("")
        loc.latitude = place.latitude
        loc.longitude = place.longitude

        val user = runBlocking { repository.getUserLocation() }

        return loc.distanceTo(user)
    }
}