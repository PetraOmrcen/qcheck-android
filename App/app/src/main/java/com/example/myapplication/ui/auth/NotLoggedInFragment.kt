package com.example.myapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.FragmentNotloggedinBinding
import com.example.myapplication.ui.base.BaseFragment


class NotLoggedInFragment : BaseFragment<AuthViewModel, FragmentNotloggedinBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navController = Navigation.findNavController(binding.root)

        val registerButton = binding.registerButton
        registerButton.setOnClickListener{
            navController.navigate(R.id.action_notloggedinFragment_to_registerFragment)
         }

        val loginButton = binding.loginButton
        loginButton.setOnClickListener{
            navController.navigate(R.id.action_notloggedinFragment_to_loginFragment)
        }

        val googleButton = binding.googleSignInButton
        googleButton.setOnClickListener{
            (activity as AuthActivity).googleSignIn()
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNotloggedinBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}