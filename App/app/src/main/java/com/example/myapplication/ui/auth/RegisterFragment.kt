package com.example.myapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.enable
import com.example.myapplication.ui.visible

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    private var email: String = ""
    private var password: String = ""
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
            if(isEmailValid(email) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }

        binding.editTextTextPassword.addTextChangedListener {
            password = it.toString()
            if(isEmailValid(email) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }

        binding.editTextTextPersonName.addTextChangedListener {
            name = it.toString()
            if(isEmailValid(email) && password.isNotEmpty() && email.isNotEmpty() && name.isNotEmpty()) {
                binding.buttonRegister.enable(true)
            }
            else binding.buttonRegister.enable(false)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}