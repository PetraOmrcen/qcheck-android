package com.example.myapplication.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.getOrAwaitValue1
import com.example.myapplication.ui.place.PlaceViewModel
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class PlaceViewModelTest : TestCase() {

    private lateinit var viewModel: PlaceViewModel
    private lateinit var repository: PlaceRepository
    private val remoteDataSource = RemoteDataSource()
    private lateinit var preferences: UserPreferences


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        preferences = UserPreferences(context)
        repository = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), preferences)
        viewModel = PlaceViewModel(repository)
    }

    @Test
    fun testGetPlace() {
        viewModel.getPlace(25)
        val result = viewModel.place.getOrAwaitValue1(2, TimeUnit.SECONDS)
        Truth.assertThat(result != null).isTrue()
    }

    @Test
    fun testIsFavourite() {
        viewModel.isFavorite(25, 17)
        val result = viewModel.isFavorite.getOrAwaitValue1(2, TimeUnit.SECONDS)
        Truth.assertThat(result != null).isTrue()
    }
}