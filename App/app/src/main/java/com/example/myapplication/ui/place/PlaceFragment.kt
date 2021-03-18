package com.example.myapplication.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.homeApp.HomeAppViewModel

class PlaceFragment : Fragment() {

    private lateinit var placeViewModel: PlaceViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        placeViewModel =
                ViewModelProvider(this).get(PlaceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_place, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}