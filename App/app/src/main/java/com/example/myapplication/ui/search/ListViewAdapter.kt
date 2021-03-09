package com.example.myapplication.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.models.Place
import java.util.*

class ListViewAdapter(var mContext: Context, placeList: MutableList<Place>?
) :
    BaseAdapter() {
    private var inflater: LayoutInflater
    private var placeList: MutableList<Place>? = null
    private var arraylist: ArrayList<Place>

    inner class ViewHolder {
        var name: TextView? = null
    }

    override fun getCount(): Int {
        return placeList!!.size
    }

    override fun getItem(position: Int): Place {
        return placeList!![position]
    }

    override fun getItemId(position: Int): Long {
        return placeList!![position].placeID
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder = view.tag as ViewHolder
        // Set the results into TextViews
        holder.name?.text = placeList!![position].placeName
        return view
    }

    // Filter Class
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase()
        placeList!!.clear()
        if (charText.isEmpty()) {
            placeList!!.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                if (wp.placeName.toLowerCase(Locale.getDefault()).contains(charText)) {
                    placeList!!.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }

    init {
        this.placeList = placeList
        inflater = LayoutInflater.from(mContext)
        arraylist = ArrayList()
        arraylist.addAll(placeList!!)
    }
}