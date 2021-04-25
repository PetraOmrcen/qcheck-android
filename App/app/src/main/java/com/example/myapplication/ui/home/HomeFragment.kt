package com.example.myapplication.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.search.SearchRecyclerAdapter


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, PlaceRepository>() {

    private var placesList = ArrayList<Place>()
    private lateinit var mContext: Context

    private lateinit var linearLayoutManagerDistance: LinearLayoutManager
    private lateinit var adapterDistance: HomeRecyclerAdapter

    private lateinit var linearLayoutManagerFavorites: LinearLayoutManager
    private lateinit var adapterFavorites: HomeRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPlaces()
        mContext = this.requireContext()

        linearLayoutManagerDistance = LinearLayoutManager(mContext)
        linearLayoutManagerDistance.orientation = LinearLayoutManager.HORIZONTAL
        binding.distanceList.layoutManager = linearLayoutManagerDistance

        linearLayoutManagerFavorites = LinearLayoutManager(mContext)
        linearLayoutManagerFavorites.orientation = LinearLayoutManager.HORIZONTAL
        binding.favoritesList.layoutManager = linearLayoutManagerFavorites

        viewModel.places.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    for (element in it.value) {
                        placesList.add(element)
                    }
                    adapterDistance = HomeRecyclerAdapter(placesList)
                    binding.distanceList.adapter = adapterDistance

                    adapterFavorites = HomeRecyclerAdapter(placesList)
                    binding.favoritesList.adapter = adapterFavorites
                }
                is Resource.Loading -> {
                    //binding.progressbar.visible(true)
                }
            }
        })

    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
        PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)
}