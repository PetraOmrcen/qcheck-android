package com.example.myapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.*
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.home.HomeActivity


class  LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

        var email: String = ""
        var password: String = ""

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

            binding.editTextTextEmailAddress.addTextChangedListener {
                email = it.toString()
                if(isEmailValid(email) && password.isNotEmpty() && email.isNotEmpty()) {
                    binding.buttonLogin.enable(true)
                }
                else binding.buttonLogin.enable(false)
            }

            binding.editTextTextPassword.addTextChangedListener {
                password = it.toString()
                if(isEmailValid(email) && password.isNotEmpty() && email.isNotEmpty()) {
                    binding.buttonLogin.enable(true)
                }
                else binding.buttonLogin.enable(false)
            }


            binding.buttonLogin.setOnClickListener {
                login()
            }

            binding.textViewRegisterNow.setOnClickListener{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        private fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        private fun login() {
            email = binding.editTextTextEmailAddress.text.toString().trim()
            password = binding.editTextTextPassword.text.toString().trim()
            binding.progressbar.visible(true)
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
