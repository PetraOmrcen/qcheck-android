package com.example.myapplication.data.repository

import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.UserApi

class PlaceRepository(
        private val api: PlaceApi
) : BaseRepository() {

    suspend fun getPlaces() = safeApiCall {
        api.getPlaces()
    }

}
