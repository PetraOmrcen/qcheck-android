package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.ui.auth.AuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var  authToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController: NavController = findNavController(R.id.nav_host_fragment)

        if(!Global.fragmentStack.empty()){
            val id = Global.fragmentStack.pop()
            navController.navigate(id)
            navView.selectedItemId = id
        }

        userPreferences = UserPreferences(this)
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    if(authToken == "null") {
                        startActivity(Intent(this, AuthActivity::class.java))
                        }
                    else {
                        navController.navigate(R.id.navigation_profile)
                    }
                }
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_map -> navController.navigate(R.id.navigation_map)
                R.id.navigation_search -> navController.navigate(R.id.navigation_search)
            }
            true
        }

       val appBarConfiguration = AppBarConfiguration(setOf(
               R.id.navigation_home, R.id.navigation_map, R.id.navigation_search, R.id.navigation_profile))
         setupActionBarWithNavController(navController, appBarConfiguration)
    }
}