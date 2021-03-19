package com.example.myapplication.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PlaceViewModel(
        private val repository: PlaceRepository
) : BaseViewModel(repository) {

    private val _place: MutableLiveData<Resource<Place>> = MutableLiveData()
    val place: LiveData<Resource<Place>>
        get() = _place

    fun getPlace(PlaceId : Long) = viewModelScope.launch {
        _place.value = Resource.Loading
        _place.value = repository.getPlace(PlaceId)
    }

    fun changeOccupancy(PlaceId : Long, place: Place) = viewModelScope.launch {
        _place.value = Resource.Loading
        _place.value = repository.changeOccupancy(PlaceId, place)
    }
}