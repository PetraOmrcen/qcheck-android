package com.example.myapplication.data.network

import com.example.myapplication.data.responses.LoginResponse
import com.example.myapplication.data.responses.Place
import retrofit2.http.GET

interface PlaceApi {

    @GET("place")
    suspend fun getPlaces(): List<Place>
}