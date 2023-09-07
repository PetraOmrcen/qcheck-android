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
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.ui.*
import com.example.myapplication.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    private var email: String = ""
    private var password: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var name: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonRegister.enable(false)

        binding.textViewLoginNow.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.editTextTextEmailAddress.addTextChangedListener {
            email = it.toString()
            if(!isEmailValid(email)) binding.textViewMailRegister.setTextColor(Color.RED)
            else binding.textViewMailRegister.setTextColor(Color.BLACK)
            if(isEmailValid(email) && isNameValid(name) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }

        binding.editTextTextPassword.addTextChangedListener {
            password = it.toString()
            if(password.isEmpty()) binding.textViewPassRegister.setTextColor(Color.RED)
            else binding.textViewPassRegister.setTextColor(Color.BLACK)
            if(isEmailValid(email) && isNameValid(name) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }

        binding.editTextTextPersonName.addTextChangedListener {
            name = it.toString()
            if(name.isEmpty()) binding.textViewNameRegister.setTextColor(Color.RED)
            else binding.textViewNameRegister.setTextColor(Color.BLACK)
            if(isEmailValid(email) && isNameValid(name) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }

        binding.buttonRegister.setOnClickListener {
            register()
        }

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.progressbar.enable(true)
                        requireView().snackbar("Successfully registered!")
                        delay(1000)
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { register() }
            }
        })
    }

    private fun register() {
        email = binding.editTextTextEmailAddress.text.toString().trim()
        password = binding.editTextTextPassword.text.toString().trim()
        firstName = binding.editTextTextPersonName.text.toString().split(" ")[0]
        lastName = binding.editTextTextPersonName.text.toString().split(" ")[1]
        binding.progressbar.visible(true)
        viewModel.register(email, password, firstName, lastName)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isNameValid(name: String): Boolean {
        return name.matches(Regex("[a-zA-Z]*[\\s]{1}[a-zA-Z].*"))
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}