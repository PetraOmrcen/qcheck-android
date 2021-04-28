package com.example.myapplication.ui.home

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.place.PlaceActivity
import com.example.myapplication.ui.visible
import kotlinx.android.synthetic.main.homelist_item.view.*
import kotlinx.android.synthetic.main.searchlist_item.view.*
import kotlinx.android.synthetic.main.searchlist_item.view.capacityProgressBar
import kotlinx.android.synthetic.main.searchlist_item.view.title
import kotlin.math.roundToInt

class HomePlaceHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    private var view: View = v
    private var place: Place? = null

    init {
        v.setOnClickListener(this)
    }

    fun bindPlace(place: Place) {
        this.place = place
        view.title.text = place.placeName + ": " + place.currentOccupancy + " / " + place.maxOccupancy
        view.capacityProgressBar.progress = ((place.currentOccupancy.toFloat() / place.maxOccupancy.toFloat()) * 100).toInt()
        if(place.distanceFromUser == null) view.homedistance.visible(false)
        else view.homedistance.text = "Distance: " + (place.distanceFromUser?.roundToInt() ?: "Nan")
    }

    override fun onClick(v: View) {
        val context = itemView.context
        Global.fragmentStack.add(R.id.navigation_home)
        val intent = Intent(context, PlaceActivity::class.java)
        place?.let { intent.putExtra(context.getString(R.string.placeId), it.id) }
        context.startActivity(intent)
    }
}