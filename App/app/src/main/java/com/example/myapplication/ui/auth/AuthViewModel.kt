package com.example.myapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Resource
import kotlinx.coroutines.launch
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.responses.LoginResponse
import com.example.myapplication.data.responses.RegisterResponse
import com.example.myapplication.ui.base.BaseViewModel

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse>>
        get() = _registerResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun google_login(
        id_token: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.google_login(id_token)
    }

    fun register(
            email: String,
            password: String,
            firstName: String,
            lastName: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(email, password, firstName, lastName)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }
}
