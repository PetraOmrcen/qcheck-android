package com.example.myapplication.data.network

import com.example.myapplication.data.responses.LoginResponse
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @Headers("Authorization: Basic cWNoZWNrLWFuZHJvaWQ6c2VjcmV0")
    @POST("oauth/token")
    suspend fun login(
            @Field("grant_type") grant_type: String,
            @Field("username") username: String,
            @Field("password") password: String
    ) : LoginResponse
}