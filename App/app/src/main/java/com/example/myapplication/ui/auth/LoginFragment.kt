package com.example.myapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.*
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.home.HomeActivity


class  LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            binding.progressbar.visible(false)
            binding.buttonLogin.enable(false)

            viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
                binding.progressbar.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            viewModel.saveAuthToken(it.value.user.access_token!!)
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                    }
                    is Resource.Failure -> handleApiError(it) { login() }
                }
            })

            binding.editTextTextPassword.addTextChangedListener {
                val email = binding.editTextTextEmailAddress.text.toString().trim()
                binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            }


            binding.buttonLogin.setOnClickListener {
                login() //pozovem fju ispod
            }
        }

        private fun login() {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()
            //binding.progressbar.visible(true)
            viewModel.login(email, password)
        }

        override fun getViewModel() = AuthViewModel::class.java

        override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
        ) = FragmentLoginBinding.inflate(inflater, container, false)

        override fun getFragmentRepository() =
            AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

    }
