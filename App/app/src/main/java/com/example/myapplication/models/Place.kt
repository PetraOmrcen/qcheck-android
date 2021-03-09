package com.example.myapplication.models

data class Place(
    val placeID: Long,
    val placeName: String,
    val currentOccupancy: Int,
    val maxOccupancy: Int)
