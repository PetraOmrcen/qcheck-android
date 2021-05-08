package com.example.myapplication.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.getOrAwaitValue1
import com.example.myapplication.ui.auth.AuthViewModel
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class AuthViewModelTest : TestCase() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var repository: AuthRepository
    private val remoteDataSource = RemoteDataSource()
    private lateinit var preferences: UserPreferences


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        preferences = UserPreferences(context)
        repository = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), preferences)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun testLogin() {
        viewModel.login("pero.peric@gmail.com", "password")
        val result = viewModel.loginResponse.getOrAwaitValue1(2, TimeUnit.SECONDS)
        assertThat(result != null).isTrue()
    }

    @Test
    fun testSaveAuthToken() {
        runBlocking { viewModel.saveAuthToken("testToken") }
        val result = runBlocking { preferences.authToken.first() }.toString()
        assertThat(result == "testToken").isTrue()
    }
}