package com.example.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.UserApi
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.base.ViewModelFactory
import com.example.myapplication.ui.makeUserFromJWT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private lateinit var  authToken: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        if(authToken == "null") {
            startActivity(Intent(context, AuthActivity::class.java))
        }

        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(authToken != "null") {
            val token = authToken
            val jwt = JWT(token)
            var user = makeUserFromJWT(jwt)
            binding.textViewProfile.text =  user.firstName + " " + user.lastName
        }

        binding.button.setOnClickListener {
            logout()
        }


    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            UserRepository(remoteDataSource.buildApi(UserApi::class.java))

}