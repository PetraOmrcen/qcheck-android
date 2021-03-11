package com.example.myapplication.data.responses

data class Place(
        val address: String,
        val city: String,
        val country: String,
        val currentOccupancy: Int,
        val id: Int,
        val latitude: Double,
        val longitude: Double,
        val maxOccupancy: Int,
        val ownerId: Int,
        val placeName: String,
        val postalCode: String
)