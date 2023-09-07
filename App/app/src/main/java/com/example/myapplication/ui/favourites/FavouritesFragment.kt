package com.example.myapplication.ui.favourites

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
import com.example.myapplication.databinding.FragmentFavouritesBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.makeUserFromJWT
import com.example.myapplication.ui.search.SearchRecyclerAdapter
import com.example.myapplication.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class FavouritesFragment : BaseFragment<HomeViewModel, FragmentFavouritesBinding, PlaceRepository>() {

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
        binding.favouritesRecyclerView.layoutManager = linearLayoutManagerFavorites

        userPreferences = UserPreferences(requireContext())
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        userLoc = runBlocking { userPreferences.userLong.first().toString()}

        if(authToken != getString(R.string.NULL_STRING)) {
            val token = authToken
            val jwt = JWT(token)
            val user = makeUserFromJWT(jwt)
            viewModel.getFavoritePlaces(user.id)
        }

        viewModel.favplaces.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbarfavourites.visible(false)
                    for (element in it.value) {
                        if(userLoc != getString(R.string.NULL_STRING)) {
                            element.distanceFromUser = viewModel.getDistance(element)
                        }
                        favplacesList.add(element)
                    }


                    adapterFavorites = SearchRecyclerAdapter(favplacesList)
                    binding.favouritesRecyclerView.adapter = adapterFavorites
                }
                is Resource.Loading -> {
                        binding.progressbarfavourites.visible(true)
                }
            }
        })
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentFavouritesBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)
}