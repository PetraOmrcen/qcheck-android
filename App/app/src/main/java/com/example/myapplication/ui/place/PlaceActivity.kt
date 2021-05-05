package com.example.myapplication.ui.place

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PlaceActivity : AppCompatActivity() {

    var placeId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        var b = intent.extras
        if(b != null) {
            placeId = b.get("PlaceId") as Long
        }
        title = ""
    }
}