package com.example.myapplication.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.responses.Place
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment() : BaseFragment<SearchViewModel, FragmentSearchBinding, PlaceRepository>() {

    private lateinit var adapter: ListViewAdapter
    private var arraylist = ArrayList<Place>()
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlaces()
        mContext = this.requireContext()

        viewModel.places.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Success -> {
                    for (element in it.value) {
                        arraylist.add(element)
                    }
                    adapter = ListViewAdapter(mContext, arraylist)
                    binding.listview.adapter = adapter
                    binding.simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(newText: String): Boolean {
                            adapter.filter(newText)
                            return false
                        }

                        override fun onQueryTextSubmit(query: String): Boolean {
                            adapter.filter(query)
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

    override fun getFragmentRepository() = PlaceRepository(remoteDataSource.buildApi(PlaceApi::class.java))

}
