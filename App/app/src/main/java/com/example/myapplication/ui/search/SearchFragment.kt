package com.example.myapplication.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.base.BaseFragment


class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding, PlaceRepository>() {

    private var placesList = ArrayList<Place>()
    private lateinit var mContext: Context

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SearchRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlaces()
        mContext = this.requireContext()

        linearLayoutManager = LinearLayoutManager(mContext)
        binding.listview.layoutManager = linearLayoutManager

        viewModel.places.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    for (element in it.value) {
                        placesList.add(element)
                    }
                    adapter = SearchRecyclerAdapter(placesList)
                    binding.listview.adapter = adapter

                    binding.simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(newText: String): Boolean {
                            adapter.filter.filter(newText)
                            return false
                        }

                        override fun onQueryTextSubmit(query: String): Boolean {
                            adapter.filter.filter(query)
                            return false
                        }
                    })
                }
                is Resource.Loading -> {
                    //binding.progressbar.visible(true)
                }
            }
        })
    }


    override fun getViewModel() = SearchViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java), userPreferences)

}
