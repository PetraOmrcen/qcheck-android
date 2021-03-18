package com.example.myapplication.ui.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myapplication.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.marker_info, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSnippet = view.findViewById<TextView>(R.id.snippet)

        val currentOccupacy = marker.snippet.split(" / ")[0].toInt()
        val maxOccupacy = marker.snippet.split(" / ")[1].toInt()
        val progress = (currentOccupacy.toFloat() / maxOccupacy.toFloat()) * 100

        view.findViewById<ProgressBar>(R.id.capacityProgressBar).progress = progress.toInt()
        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}