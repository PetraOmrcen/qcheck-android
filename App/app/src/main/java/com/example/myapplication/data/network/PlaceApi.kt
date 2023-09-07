package com.example.myapplication.data.network

import com.example.myapplication.data.responses.Place
import retrofit2.http.*

interface PlaceApi {

    @GET("place")
    suspend fun getPlaces(): List<Place>

    @GET("app_user/{id}/favourites")
    suspend fun getFavoritePlaces(@Path("id") userId : Long): List<Place>

    @GET("app_user/{id}/places")
    suspend fun getMyPlaces(@Path("id") userId : Long): List<Place>

    @GET("place/{id}")
    suspend fun getPlace(@Path("id") PlaceId : Long): Place

    @Headers("Content-Type: application/json")
    @PUT("place/{id}")
    suspend fun changeOccupancy(
            @Path("id") placeId: Long,
            @Body place: Place)
    : Place

    @Headers("Content-Type: application/json")
    @PUT("app_user/{user_id}/favourites/{place_id}")
    suspend fun setFavorite(
            @Path("place_id") placeId: Long,
            @Path("user_id") userId: Long)

    @Headers("Content-Type: application/json")
    @DELETE("app_user/{user_id}/favourites/{place_id}")
    suspend fun removeFavorite(
            @Path("place_id") placeId: Long,
            @Path("user_id") userId: Long)

    @Headers("Content-Type: application/json")
    @GET("app_user/{user_id}/favourites/{place_id}")
    suspend fun isFavorite(
            @Path("place_id") placeId: Long,
            @Path("user_id") userId: Long)
    : Boolean

}