package com.example.myapplication.data.repository

import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.responses.RegisterRequest
import com.example.myapplication.data.responses.RegisterResponse

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login("password", email, password)
    }

    suspend fun register(
            email: String,
            password: String,
            firstName: String,
            lastName: String
    ) = safeApiCall {
        val user = RegisterRequest(email, firstName, lastName, password, "null", 0, "null")
        api.register(user)
    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

}