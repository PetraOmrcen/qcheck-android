package com.example.myapplication.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.ui.base.BaseFragment

class PlaceFragment : BaseFragment<PlaceViewModel, FragmentPlaceBinding, PlaceRepository>() {

    private lateinit var place : Place

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val placeId = (activity as PlaceActivity).placeId

        viewModel.getPlace(placeId)



        viewModel.place.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    this.place = it.value

                    binding.textViewProgress.text = place.currentOccupancy.toString() + " / " + place.maxOccupancy.toString()
                    binding.textViewPlaceName.text = place.placeName
                    binding.progressBar.progress = ((place.currentOccupancy.toFloat() / place.maxOccupancy.toFloat()) * 100).toInt()
                    binding.textViewAddressPlace.text = place.address
                }
                is Resource.Loading -> {
                    //binding.progressbar.visible(true)
                }
            }
        })

        binding.buttonDecr.setOnClickListener{
            place.currentOccupancy -= 1
            viewModel.changeOccupancy(place.id, place)
        }

        binding.buttonIncr.setOnClickListener{
            place.currentOccupancy += 1
            viewModel.changeOccupancy(place.id, place)
        }
    }

    override fun getViewModel() = PlaceViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentPlaceBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java))
}