package com.example.myapplication.data.repository

import android.location.Location
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.responses.Place

class PlaceRepository (private val api: PlaceApi, private val preferences: UserPreferences) : BaseRepository() {

    suspend fun getPlaces() = safeApiCall {
        api.getPlaces()
    }

    suspend fun getPlace(PlaceId : Long) = safeApiCall {
        api.getPlace(PlaceId)
    }

    suspend fun changeOccupancy(placeId : Long, place: Place) = safeApiCall {
        api.changeOccupancy(placeId, place)
    }

    suspend fun saveUserLocation(location: Location){
        preferences.saveUserLocation(location.latitude.toString(), location.longitude.toString())
    }

}
