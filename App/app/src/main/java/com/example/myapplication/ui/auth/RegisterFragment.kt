package com.example.myapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.ui.base.BaseFragment

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.textViewLoginNow.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}