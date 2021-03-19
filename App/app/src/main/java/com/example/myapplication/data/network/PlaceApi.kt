package com.example.myapplication.data.network

import com.example.myapplication.data.responses.Place
import retrofit2.http.*

interface PlaceApi {

    @GET("place")
    suspend fun getPlaces(): List<Place>

    @GET("place/{path_variable}")
    suspend fun getPlace(@Path("path_variable") PlaceId : Long): Place

    @Headers("Content-Type: application/json")
    @PUT("place/{id}")
    suspend fun changeOccupancy(
            @Path("id") placeId: Long,
            @Body place: Place)
    : Place
}