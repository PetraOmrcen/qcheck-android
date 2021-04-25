package com.example.myapplication.ui.search

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.inflate
import kotlin.collections.ArrayList

class RecyclerAdapter(private val places: ArrayList<Place>): RecyclerView.Adapter<PlaceHolder>(), Filterable {

    var placesList = ArrayList<Place>(places)
    var placesListFilter = ArrayList<Place>(places)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val inflatedView = parent.inflate(R.layout.listview_item, false)
        return PlaceHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val itemPlace = placesList[position]
        holder.bindPlace(itemPlace)
    }

    override fun getItemCount() = placesList.size

    override fun getFilter(): Filter {

        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = placesListFilter.size
                    filterResults.values = placesListFilter
                } else {
                    var searchChar: String = constraint.toString().toLowerCase()
                    var placesList = ArrayList<Place>()
                    for (place in placesListFilter) {
                        if (place.placeName.toLowerCase().contains(searchChar)) {
                            placesList.add(place)
                        }
                    }
                    filterResults.count = placesList.size
                    filterResults.values = placesList
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    placesList = results.values as ArrayList<Place>
                }
                notifyDataSetChanged()
            }

        }
    }

}