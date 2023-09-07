package com.example.myapplication.ui.place

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PlaceActivity : AppCompatActivity() {

    var placeId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        val bundle = intent.extras
        if(bundle != null) {
            placeId = bundle.get("PlaceId") as Long
        }
        title = ""
    }
}