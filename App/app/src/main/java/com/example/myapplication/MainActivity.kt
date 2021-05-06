package com.example.myapplication


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.map.MapFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var  authToken: String
    private var googleAcc: Boolean = false
    private lateinit var  account: GoogleSignInAccount
    private lateinit var binding: ActivityMainBinding

    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MapFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 10f, locationListener)

        val navView: BottomNavigationView = binding.navView

        val navController: NavController = findNavController(R.id.nav_host_fragment)

        if(!Global.fragmentStack.empty()){
            val id = Global.fragmentStack.pop()
            navController.navigate(id)
            navView.selectedItemId = id
        }

        authToken = runBlocking { userPreferences.authToken.first() }.toString()
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            account = GoogleSignIn.getLastSignedInAccount(this)!!
            googleAcc = true
        }

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    if (authToken == getString(R.string.NULL_STRING) && !googleAcc) {
                        startActivity(Intent(this, AuthActivity::class.java))
                    } else {
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

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            runBlocking { userPreferences.saveUserLocation(location.latitude.toString(), location.longitude.toString()) }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}