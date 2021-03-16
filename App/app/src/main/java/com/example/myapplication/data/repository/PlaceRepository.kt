package com.example.myapplication.data.repository

import com.example.myapplication.data.network.PlaceApi

class PlaceRepository (private val api: PlaceApi) : BaseRepository() {

    suspend fun getPlaces() = safeApiCall {
        api.getPlaces()
    }
}