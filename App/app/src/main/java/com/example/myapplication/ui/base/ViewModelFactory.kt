package com.example.myapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.BaseRepository
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.map.MapViewModel
import com.example.myapplication.ui.place.PlaceViewModel
import com.example.myapplication.ui.profile.ProfileViewModel
import com.example.myapplication.ui.search.SearchViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository as PlaceRepository) as T
            modelClass.isAssignableFrom(MapViewModel::class.java) -> MapViewModel(repository as PlaceRepository) as T
            modelClass.isAssignableFrom(PlaceViewModel::class.java) -> PlaceViewModel(repository as PlaceRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as PlaceRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}