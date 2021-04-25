package com.example.myapplication.ui.home

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import com.example.myapplication.ui.place.PlaceActivity
import kotlinx.android.synthetic.main.searchlist_item.view.*

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
    }

    override fun onClick(v: View) {
        val context = itemView.context
        Global.fragmentStack.add(R.id.navigation_home)
        val intent = Intent(context, PlaceActivity::class.java)
        place?.let { intent.putExtra("PlaceId", it.id) }
        context.startActivity(intent)
    }
}