package com.example.myapplication.data.network

import com.example.myapplication.data.responses.LoginResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @Headers("Authorization: Basic cWNoZWNrLWFuZHJvaWQ6c2VjcmV0")
    @POST("oauth/token?grant_type=password&username=pero.peric@gmail.com&password=password")
    suspend fun login(

    ) : LoginResponse
}