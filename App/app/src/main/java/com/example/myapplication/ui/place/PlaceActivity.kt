package com.example.myapplication.ui.place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class PlaceActivity : AppCompatActivity() {

    var placeId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        placeId = this.intent.getLongExtra(getString(R.string.placeId), 0)
        title = ""
    }
}