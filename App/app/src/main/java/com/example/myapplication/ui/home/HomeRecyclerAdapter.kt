package com.example.myapplication.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.inflate
import com.example.myapplication.ui.search.SearchPlaceHolder

class HomeRecyclerAdapter(private val places: ArrayList<Place>): RecyclerView.Adapter<HomePlaceHolder>(){

    var placesList = ArrayList<Place>(places)
    var placesListFilter = ArrayList<Place>(places)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePlaceHolder {
        val inflatedView = parent.inflate(R.layout.homelist_item, false)
        return HomePlaceHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HomePlaceHolder, position: Int) {
        val itemPlace = placesList[position]
        holder.bindPlace(itemPlace)
    }

    override fun getItemCount() = placesList.size

}