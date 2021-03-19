package com.example.myapplication.ui.homeApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R


class HomeAppFragment : Fragment() {

    private lateinit var homeAppViewModel: HomeAppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeAppViewModel =
            ViewModelProvider(this).get(HomeAppViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_homeapp, container, false)
        return root
    }
}