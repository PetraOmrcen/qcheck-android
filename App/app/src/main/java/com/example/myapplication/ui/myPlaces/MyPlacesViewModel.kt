package com.example.myapplication.ui.myPlaces

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

class MyPlacesViewModel(
    private val repository: PlaceRepository
) : BaseViewModel(repository) {

    private val _myplaces: MutableLiveData<Resource<List<Place>>> = MutableLiveData()
    val myplaces: LiveData<Resource<List<Place>>>
        get() = _myplaces

    fun getMyPlaces(id: Int) = viewModelScope.launch {
        _myplaces.value = Resource.Loading
        _myplaces.value = repository.getMyPlaces(id)
    }

    fun getDistance(place: Place): Float {
        val loc = Location("")
        loc.latitude = place.latitude
        loc.longitude = place.longitude

        val user = runBlocking { repository.getUserLocation() }

        return loc.distanceTo(user)
    }
}