package com.example.myapplication.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Validator
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.*
import com.example.myapplication.ui.base.BaseFragment
import kotlinx.coroutines.delay


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
                            viewModel.saveAuthToken(it.value.access_token)
                            binding.progressbar.enable(true)
                            requireView().snackbar("Successful login!")
                            delay(1000)
                            requireActivity().startNewActivity(MainActivity::class.java)
                        }
                    }
                    is Resource.Failure -> handleApiError(it) { login() }
                }
            })

            binding.editTextTextEmailAddress.addTextChangedListener {
                email = it.toString()
                if(!Validator.isEmailValid(email)) binding.textViewMail.setTextColor(Color.RED)
                else binding.textViewMail.setTextColor(Color.BLACK)
                if(Validator.isEmailValid(email) && password.isNotEmpty()) {
                    binding.buttonLogin.enable(true)
                }
                else binding.buttonLogin.enable(false)
            }

            binding.editTextTextPassword.addTextChangedListener {
                password = it.toString()
                if(password.isEmpty()) binding.textViewPass.setTextColor(Color.RED)
                else binding.textViewPass.setTextColor(Color.BLACK)
                if(Validator.isEmailValid(email) && password.isNotEmpty()) {
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
