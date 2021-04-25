package com.example.myapplication.ui.map

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.databinding.MarkerInfoBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    private var mWindow: View = (context as Activity).layoutInflater.inflate(R.layout.marker_info, null)

    private fun rendowWindowText(marker: Marker, view: View){
        val binding = MarkerInfoBinding.bind(view)

        val tvTitle = binding.title
        val tvSnippet = binding.snippet

        val currentOccupacy = marker.snippet.split(" / ")[0].toInt()
        val maxOccupacy = marker.snippet.split(" / ")[1].toInt()
        val progress = (currentOccupacy.toFloat() / maxOccupacy.toFloat()) * 100

        binding.capacityProgressBar.progress = progress.toInt()
        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}
