package com.example.myapplication.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.auth0.android.jwt.JWT
import com.example.myapplication.R
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.makeUserFromJWT
import com.example.myapplication.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class PlaceFragment : BaseFragment<PlaceViewModel, FragmentPlaceBinding, PlaceRepository>() {

    private lateinit var place : Place
    private lateinit var  authToken: String
    private var userId by Delegates.notNull<Long>()
    private var isFavorite by Delegates.notNull<Boolean>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val placeId = (activity as PlaceActivity).placeId

        viewModel.getPlace(placeId)

        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        if(authToken != getString(R.string.NULL_STRING)) {
            val token = authToken
            val jwt = JWT(token)
            val user = makeUserFromJWT(jwt)
            userId = user.id.toLong()
            viewModel.isFavorite(placeId, userId)
            binding.favoriteButton.isEnabled = true
            binding.favoriteButton.isVisible = true
        }else {
            binding.favoriteButton.isEnabled = false
            binding.favoriteButton.isVisible = false

            binding.buttonDecr.isEnabled = false
            binding.buttonIncr.isEnabled = false
            binding.buttonDecr.isVisible = false
            binding.buttonIncr.isVisible = false
        }

        viewModel.place.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    this.place = it.value
                    binding.progressbarplace.visible(false)

                    binding.textViewProgress.text = place.currentOccupancy.toString() + " / " + place.maxOccupancy.toString()
                    binding.textViewPlaceName.text = place.placeName
                    binding.progressBar.progress = ((place.currentOccupancy.toFloat() / place.maxOccupancy.toFloat()) * 100).toInt()
                    binding.textViewAddressPlace.text = place.address
                }
                is Resource.Loading -> {
                    binding.progressbarplace.visible(true)
                }
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    this.isFavorite = it.value
                    if(isFavorite)
                        binding.favoriteButton.setBackgroundResource(R.drawable.favorites_red)
                    else
                        binding.favoriteButton.setBackgroundResource(R.drawable.favourites_darkblue)
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

        binding.favoriteButton.setOnClickListener{
            if(isFavorite) {
                viewModel.removeFavorite(placeId, userId)
            }
            else{
                viewModel.setFavorite(placeId, userId)
            }
        }
    }

    override fun getViewModel() = PlaceViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentPlaceBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)
}