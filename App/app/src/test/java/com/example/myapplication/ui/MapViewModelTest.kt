package com.example.myapplication.ui

import android.content.Context
import android.location.Location
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.getOrAwaitValue1
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.map.MapViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MapViewModelTest : TestCase() {

    private lateinit var viewModel: MapViewModel
    private lateinit var repository: PlaceRepository
    private val remoteDataSource = RemoteDataSource()
    private lateinit var preferences: UserPreferences


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        preferences = UserPreferences(context)
        repository = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), preferences)
        viewModel = MapViewModel(repository)
    }

    @Test
    fun testGetPlaces() {
        viewModel.getPlaces()
        val result = viewModel.places.getOrAwaitValue1(2, TimeUnit.SECONDS)
        Truth.assertThat(result != null).isTrue()
    }

    @Test
    fun testSaveUserLocation() {
        var location:Location = Location("")
        location.latitude = 0.0
        location.longitude = 0.0

        runBlocking { viewModel.saveUserLocation(location) }
        val resultLat = runBlocking { preferences.userLat.first() }.toString()
        val resultLong = runBlocking { preferences.userLong.first() }.toString()

        Truth.assertThat(resultLat == "0.0").isTrue()
        Truth.assertThat(resultLong == "0.0").isTrue()
    }

}