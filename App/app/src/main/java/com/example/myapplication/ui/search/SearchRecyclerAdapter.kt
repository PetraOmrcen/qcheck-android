package com.example.myapplication.ui.search

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.inflate
import java.util.*
import kotlin.collections.ArrayList

class SearchRecyclerAdapter(places: ArrayList<Place>): RecyclerView.Adapter<SearchPlaceHolder>(), Filterable {

    var placesList = ArrayList<Place>(places)
    var placesListFilter = ArrayList<Place>(places)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceHolder {
        val inflatedView = parent.inflate(R.layout.searchlist_item, false)
        return SearchPlaceHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SearchPlaceHolder, position: Int) {
        val itemPlace = placesList[position]
        holder.bindPlace(itemPlace)
    }

    override fun getItemCount() = placesList.size

    override fun getFilter(): Filter {

        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = placesListFilter.size
                    filterResults.values = placesListFilter
                } else {
                    val searchChar: String = constraint.toString().toLowerCase(Locale.ROOT)
                    val placesList = ArrayList<Place>()
                    for (place in placesListFilter) {
                        if (place.placeName.toLowerCase(Locale.ROOT).contains(searchChar)) {
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