package com.example.myapplication.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.getOrAwaitValue1
import com.example.myapplication.ui.search.SearchViewModel
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest : TestCase() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: PlaceRepository
    private val remoteDataSource = RemoteDataSource()
    private lateinit var preferences: UserPreferences


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        preferences = UserPreferences(context)
        repository = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), preferences)
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun testGetPlaces() {
        viewModel.getPlaces()
        val result = viewModel.places.getOrAwaitValue1(2, TimeUnit.SECONDS)
        Truth.assertThat(result != null).isTrue()
    }
}