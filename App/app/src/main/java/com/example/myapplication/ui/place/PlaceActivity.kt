package com.example.myapplication.ui.place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceActivity : AppCompatActivity() {
    private var prog = 0
    private var max_capacity = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        updateProgressBar()

        button_incr.setOnClickListener {
            if (prog <= max_capacity) {
                prog += 1
                updateProgressBar()
            }
        }

        button_decr.setOnClickListener {
            if (prog >= max_capacity) {
                prog -= 1
                updateProgressBar()
            }
        }

    }
    private fun updateProgressBar(){

        progress_bar.progress = prog
        text_view_progress.text = "$prog%"
   }
}