package com.example.myapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.base.BaseViewModel

class ProfileViewModel(
        private val repository: UserRepository
) : BaseViewModel(repository) {

}