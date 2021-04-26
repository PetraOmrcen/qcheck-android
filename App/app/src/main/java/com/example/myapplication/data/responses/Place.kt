package com.example.myapplication.data.responses

data class Place(
        val address: String,
        val city: String,
        val country: String,
        var currentOccupancy: Int,
        val id: Long,
        val latitude: Double,
        val longitude: Double,
        val maxOccupancy: Int,
        val ownerId: Int,
        val placeName: String,
        val postalCode: String,
        var distanceFromUser: Float?
)
