package com.example.myapplication.data.repository

import android.location.Location
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.responses.Place
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PlaceRepository (private val api: PlaceApi, private val preferences: UserPreferences) : BaseRepository() {

    suspend fun getPlaces() = safeApiCall {
        api.getPlaces()
    }

    suspend fun getFavoritePlaces(id: Int) = safeApiCall {
        api.getFavoritePlaces(id.toLong())
    }

    suspend fun getMyPlaces(id: Int) = safeApiCall {
        api.getMyPlaces(id.toLong())
    }

    suspend fun getPlace(PlaceId : Long) = safeApiCall {
        api.getPlace(PlaceId)
    }

    suspend fun changeOccupancy(placeId : Long, place: Place) = safeApiCall {
        api.changeOccupancy(placeId, place)
    }

    suspend fun setFavorite(placeId : Long, userId: Long) = safeApiCall {
        api.setFavorite(placeId, userId)
    }

    suspend fun removeFavorite(placeId : Long, userId: Long) = safeApiCall {
        api.removeFavorite(placeId, userId)
    }

    suspend fun isFavorite(placeId : Long, userId: Long) = safeApiCall {
        api.isFavorite(placeId, userId)
    }

    suspend fun saveUserLocation(location: Location){
        preferences.saveUserLocation(location.latitude.toString(), location.longitude.toString())
    }

    suspend fun getUserLocation(): Location{
        val user = Location("")
        val lat = runBlocking { preferences.userLat.first() }.toString()
        val long = runBlocking { preferences.userLong.first() }.toString()
        if(lat != NULL_STRING && long != NULL_STRING) {
            user.latitude = lat.toDouble()
            user.longitude = long.toDouble()
        }

        return user
    }

}
