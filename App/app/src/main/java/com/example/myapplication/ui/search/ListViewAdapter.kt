package com.example.myapplication.ui.search

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.responses.Place
import java.util.*

class ListViewAdapter(var mContext: Context,
                      placeList: MutableList<Place>?
) :
    BaseAdapter() {
    private var inflater: LayoutInflater
    private var placeList: MutableList<Place>? = null
    private var arraylist: ArrayList<Place>

    override fun getCount(): Int {
        return placeList!!.size
    }

    override fun getItem(position: Int): Place {
        return placeList!![position]
    }

    override fun getItemId(position: Int): Long {
        return placeList!![position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val inflater = mContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.listview_item, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        //val imageView = rowView.findViewById(R.id.icon) as ImageView
        val progressBar = rowView.findViewById(R.id.capacityProgressBar) as ProgressBar

        titleText.text = placeList!![position].placeName + ": " + placeList!![position].currentOccupancy + " / " + placeList!![position].maxOccupancy
        //imageView.setImageResource(imgid[position])
        progressBar.progress = ((placeList!![position].currentOccupancy.toFloat() / placeList!![position].maxOccupancy.toFloat()) * 100).toInt()

        return rowView
    }

    // Filter Class
    fun filter(charText: String) {
        var text = charText
        text = text.toLowerCase(Locale.ROOT)
        placeList!!.clear()
        if (text.isEmpty()) {
            placeList!!.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                if (wp.placeName.toLowerCase(Locale.getDefault()).contains(text)) {
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