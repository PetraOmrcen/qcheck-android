package com.example.myapplication.data.responses

data class LoginResponse(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val expires_in: Int,
    val scope: String,
    val firstName: String,
    val lastName: String,
    val userId: String,
    val email: String,
    val jti: String
)