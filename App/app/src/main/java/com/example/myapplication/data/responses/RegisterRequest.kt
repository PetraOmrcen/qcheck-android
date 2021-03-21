package com.example.myapplication.data.responses

data class RegisterRequest(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val phoneNumber: String?,
        val roleId: Int,
        val username: String?
)