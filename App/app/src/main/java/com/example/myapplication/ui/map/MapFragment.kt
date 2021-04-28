package com.example.myapplication.ui.map


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.place.PlaceActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.runBlocking


class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding, PlaceRepository>(), OnMapReadyCallback {
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private var locationPermissionGranted = false
    private lateinit var placesClient: PlacesClient
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(45.80161397098212, 15.970883667124058)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var arraylist = ArrayList<Place>()
    private var markers = ArrayList<Marker?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = binding.mapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(activity?.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        activity?.applicationContext?.let { Places.initialize(it, getString(R.string.google_maps_key)) }
        placesClient = activity?.let { Places.createClient(it) }!!
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        viewModel.getPlaces()

        mMapView?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    override fun onMapReady(mMap: GoogleMap?) {
        googleMap = mMap

        getLocationPermission()

        getDeviceLocation()

        updateLocationUI()

        googleMap?.setInfoWindowAdapter(activity?.let { CustomInfoWindowForGoogleMap(it) })
        googleMap?.moveCamera(CameraUpdateFactory.zoomTo(15F))
        googleMap?.moveCamera((CameraUpdateFactory.newLatLng(defaultLocation)))



        viewModel.places.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Success -> {
                    for (element in it.value)
                    {
                        arraylist.add(element)
                        val elLocation = LatLng(element.latitude, element.longitude)
                        markers.add(
                                googleMap?.addMarker(
                                        MarkerOptions().position(elLocation).title(element.placeName).snippet(
                                                element.currentOccupancy.toString() + " / " + element.maxOccupancy.toString())))
                    }
                }
            }
        })

        googleMap?.setOnInfoWindowClickListener {
            Global.fragmentStack.add(R.id.navigation_map)
            val intent = Intent(context, PlaceActivity::class.java)
            val place = arraylist[markers.indexOf(it)]
            intent.putExtra(getString(R.string.placeId), place.id)
            startActivity(intent)
        }
    }

    public fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                googleMap?.isMyLocationEnabled = false
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            //Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                activity?.let {
                    locationResult.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.result
                            if (lastKnownLocation != null) {
                                runBlocking { viewModel.saveUserLocation(lastKnownLocation!!) }
                                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                            }
                        } else {
                            //Log.d(TAG, "Current location is null. Using defaults.")
                            //Log.e(TAG, "Exception: %s", task.exception)
                            googleMap?.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                            googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            //Log.e("Exception: %s", e.message, e)
        }
    }

    override fun getViewModel() = MapViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 15
    }

}