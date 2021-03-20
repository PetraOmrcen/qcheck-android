package com.example.myapplication.data.repository

import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi

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

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

}