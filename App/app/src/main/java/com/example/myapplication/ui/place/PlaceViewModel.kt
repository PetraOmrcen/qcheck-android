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

    private val _isFavorite: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val isFavorite: LiveData<Resource<Boolean>>
        get() = _isFavorite

    private val _isAllowed: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val isAllowed: LiveData<Resource<Boolean>>
        get() = _isAllowed

    fun getPlace(PlaceId : Long) = viewModelScope.launch {
        _place.value = Resource.Loading
        _place.value = repository.getPlace(PlaceId)
    }

    fun changeOccupancy(PlaceId : Long, place: Place) = viewModelScope.launch {
        _place.value = Resource.Loading
        _place.value = repository.changeOccupancy(PlaceId, place)
    }

    fun setFavorite(PlaceId : Long, userId: Long) = viewModelScope.launch {
        repository.setFavorite(PlaceId, userId)
        _isFavorite.value = Resource.Loading
        _isFavorite.value = repository.isFavorite(PlaceId, userId)
    }

    fun removeFavorite(PlaceId : Long, userId: Long) = viewModelScope.launch {
        repository.removeFavorite(PlaceId, userId)
        _isFavorite.value = Resource.Loading
        _isFavorite.value = repository.isFavorite(PlaceId, userId)
    }

    fun isFavorite(PlaceId : Long, userId: Long) = viewModelScope.launch {
        _isFavorite.value = Resource.Loading
        _isFavorite.value = repository.isFavorite(PlaceId, userId)
    }

    fun isAllowed(PlaceId : Long, userId: Long) = viewModelScope.launch {
        _isAllowed.value = Resource.Loading
        _isAllowed.value = repository.isAllowed(PlaceId, userId)
    }
}