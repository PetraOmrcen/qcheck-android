package com.example.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.PlaceApi
import com.example.myapplication.data.network.UserApi
import com.example.myapplication.data.repository.PlaceRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.place.PlaceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private lateinit var  authToken: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        if(authToken == "null") {
            startActivity(Intent(context, AuthActivity::class.java))
        }

        binding.textViewToken.text = authToken


    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            UserRepository(remoteDataSource.buildApi(UserApi::class.java))

}