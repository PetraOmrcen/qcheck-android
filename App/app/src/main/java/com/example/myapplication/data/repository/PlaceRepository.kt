package com.example.myapplication.data.repository

import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.responses.Place
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRepository (private val api: PlaceApi) : BaseRepository() {

    suspend fun getPlaces() = safeApiCall {
        api.getPlaces()
    }

    suspend fun getPlace(PlaceId : Long) = safeApiCall {
        api.getPlace(PlaceId)
    }

    suspend fun changeOccupancy(placeId : Long, place: Place) = safeApiCall {
        api.changeOccupancy(placeId, place)
    }

}
