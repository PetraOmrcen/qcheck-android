package com.example.myapplication.data.responses

data class RegisterResponse(
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val roleId: Int,
    val username: String
)