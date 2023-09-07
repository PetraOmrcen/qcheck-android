package com.example.myapplication.data.responses


data class User(
    val access_token: String?,
    val created_at: String?,
    val email: String,
    val id: Int,
    val firstName: String,
    val lastName: String,
    val roleId: String
)