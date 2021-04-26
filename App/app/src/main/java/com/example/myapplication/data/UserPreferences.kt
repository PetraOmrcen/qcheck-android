package com.example.myapplication.data

//import android.content.Context
import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.myapplication.data.responses.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
        name = "my_data_store"
    )

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val userLat: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_LAT]
        }

    val userLong: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_LONG]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserLocation(lat: String, long: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_LAT] = lat
            preferences[KEY_USER_LONG] = long
            preferences[KEY_USER_LOC] = "1"
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val KEY_USER_LOC = preferencesKey<String>("key_user_loc")
        private val KEY_USER_LAT = preferencesKey<String>("key_user_lat")
        private val KEY_USER_LONG = preferencesKey<String>("key_user_long")
    }
}