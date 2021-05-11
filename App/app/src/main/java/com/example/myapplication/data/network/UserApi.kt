package com.example.myapplication.data.network

import com.example.myapplication.data.responses.User
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("app_user/logout")
    suspend fun logout(): ResponseBody
}