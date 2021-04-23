package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.ui.place.PlaceActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton


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

        val googleButton = root.findViewById(R.id.googleSignInButton) as SignInButton
        googleButton.setOnClickListener{
            (activity as AuthActivity).googleSignIn()
        }

        return root
    }
}