package com.example.myapplication.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class HomeViewModel(
    private val repository: PlaceRepository
) : BaseViewModel(repository) {

    private val _places: MutableLiveData<Resource<List<Place>>> = MutableLiveData()
    val places: LiveData<Resource<List<Place>>>
        get() = _places

    private val _favplaces: MutableLiveData<Resource<List<Place>>> = MutableLiveData()
    val favplaces: LiveData<Resource<List<Place>>>
        get() = _favplaces

    fun getPlaces() = viewModelScope.launch {
        _places.value = Resource.Loading
        _places.value = repository.getPlaces()
    }

    fun getFavoritePlaces(id: Int) = viewModelScope.launch {
        _favplaces.value = Resource.Loading
        _favplaces.value = repository.getFavoritePlaces(id)
    }

    fun getDistance(place: Place): Float {
        var loc = Location("")
        loc.latitude = place.latitude
        loc.longitude = place.longitude

        var user = runBlocking { repository.getUserLocation() }

        return loc.distanceTo(user)
    }
}