package com.example.myapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R


class NotLoggedInFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_notloggedin, container, false)

        val registerButton = root.findViewById(R.id.register_button) as Button
        registerButton.setOnClickListener{
            Navigation.findNavController(root).navigate(R.id.action_notloggedinFragment_to_registerFragment)
         }

        val loginButton = root.findViewById(R.id.login_button) as Button
        loginButton.setOnClickListener{
            Navigation.findNavController(root).navigate(R.id.action_notloggedinFragment_to_loginFragment)
        }

        return root
    }
}