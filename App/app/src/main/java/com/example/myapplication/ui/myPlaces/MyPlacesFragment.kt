package com.example.myapplication.ui.myPlaces

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import com.example.myapplication.R
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentMyplacesBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.makeUserFromJWT
import com.example.myapplication.ui.search.SearchRecyclerAdapter
import com.example.myapplication.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MyPlacesFragment : BaseFragment<MyPlacesViewModel, FragmentMyplacesBinding, PlaceRepository>() {

    private var favplacesList = ArrayList<Place>()
    private lateinit var mContext: Context
    private lateinit var  authToken: String

    private lateinit var linearLayoutManagerFavorites: LinearLayoutManager
    private lateinit var adapterFavorites: SearchRecyclerAdapter

    private lateinit var  userLoc: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = this.requireContext()

        linearLayoutManagerFavorites = LinearLayoutManager(mContext)
        linearLayoutManagerFavorites.orientation = LinearLayoutManager.VERTICAL
        binding.myplacesRecyclerView.layoutManager = linearLayoutManagerFavorites

        userPreferences = UserPreferences(requireContext())
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        userLoc = runBlocking { userPreferences.userLong.first().toString()}

        if(authToken != getString(R.string.NULL_STRING)) {
            val token = authToken
            val jwt = JWT(token)
            val user = makeUserFromJWT(jwt)
            viewModel.getMyPlaces(user.id)
        }

        viewModel.myplaces.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbarmyplaces.visible(false)
                    for (element in it.value) {
                        if(userLoc != getString(R.string.NULL_STRING)) {
                            element.distanceFromUser = viewModel.getDistance(element)
                        }
                        favplacesList.add(element)
                    }


                    adapterFavorites = SearchRecyclerAdapter(favplacesList)
                    binding.myplacesRecyclerView.adapter = adapterFavorites
                }
                is Resource.Loading -> {
                    binding.progressbarmyplaces.visible(true)
                }
            }
        })
    }

    override fun getViewModel() = MyPlacesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyplacesBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
        PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)
}