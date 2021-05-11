package com.example.myapplication.data.repository

import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.responses.RegisterRequest

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

    suspend fun google_login(token: String) = safeApiCall {
        api.google_login(token)
    }

    suspend fun register(
            email: String,
            password: String,
            firstName: String,
            lastName: String
    ) = safeApiCall {
        val user = RegisterRequest(email, firstName, lastName, password, NULL_STRING, 0, NULL_STRING)
        api.register(user)
    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

}