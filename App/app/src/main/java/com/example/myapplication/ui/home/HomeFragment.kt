package com.example.myapplication.ui.home

import android.R.attr.data
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import com.example.myapplication.R
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.makeUserFromJWT
import com.example.myapplication.ui.map.MapFragment
import com.example.myapplication.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, PlaceRepository>() {

    private var placesList = ArrayList<Place>()
    private var favplacesList = ArrayList<Place>()
    private lateinit var mContext: Context
    private lateinit var  authToken: String

    private lateinit var linearLayoutManagerDistance: LinearLayoutManager
    private lateinit var adapterDistance: HomeRecyclerAdapter

    private lateinit var linearLayoutManagerFavorites: LinearLayoutManager
    private lateinit var adapterFavorites: HomeRecyclerAdapter

    private lateinit var  userLoc: String

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

        userPreferences = UserPreferences(requireContext())
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        userLoc = runBlocking { userPreferences.userLong.first().toString()}

        if(authToken != getString(R.string.NULL_STRING)) {
            val token = authToken
            val jwt = JWT(token)
            var user = makeUserFromJWT(jwt)
            viewModel.getFavoritePlaces(user.id)
        }else {
            binding.textViewFavorites.isInvisible = true
            binding.progressbarhomefavorites.isInvisible = true
        }

        viewModel.places.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbarhomeclosest.visible(false)
                    for (element in it.value) {
                        if(userLoc != getString(R.string.NULL_STRING)) {
                            element.distanceFromUser = viewModel.getDistance(element)
                        }
                        placesList.add(element)
                    }

                    if(userLoc != getString(R.string.NULL_STRING)) {
                        placesList.sortWith(Comparator { place1, place2 ->
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            if (place1.distanceFromUser!! < place2.distanceFromUser!!) -1 else if (place1.distanceFromUser!! > place2.distanceFromUser!!) 1 else 0
                        })
                    }

                    adapterDistance = HomeRecyclerAdapter(placesList)
                    binding.distanceList.adapter = adapterDistance
                }
                is Resource.Loading -> {
                    binding.progressbarhomeclosest.visible(true)
                }
            }
        })


        viewModel.favplaces.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbarhomefavorites.visible(false)
                    for (element in it.value) {
                        if(userLoc != getString(R.string.NULL_STRING)) {
                            element.distanceFromUser = viewModel.getDistance(element)
                        }
                        favplacesList.add(element)
                    }

                    binding.textViewFavorites.isInvisible = false

                    adapterFavorites = HomeRecyclerAdapter(favplacesList)
                    binding.favoritesList.adapter = adapterFavorites
                }
                is Resource.Loading -> {
                    if(authToken == getString(R.string.NULL_STRING)) {
                        binding.progressbarhomefavorites.visible(false)
                    }else{
                        binding.progressbarhomefavorites.visible(true)
                    }
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