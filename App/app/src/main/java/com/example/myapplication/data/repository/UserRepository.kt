package com.example.myapplication.data.repository

import com.example.myapplication.data.network.UserApi

class UserRepository(
    private val api: UserApi
) : BaseRepository() {
}