package com.example.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.auth.AuthActivity

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startActivity(Intent(context, AuthActivity::class.java))

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        //val root = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        val registerButton = root.findViewById(R.id.register_button) as Button
//        registerButton.setOnClickListener{
//            startActivity(Intent(context, AuthActivity::class.java))
//         }
//
//        val loginButton = root.findViewById(R.id.login_button) as Button
//        loginButton.setOnClickListener{
//            startActivity(Intent(context, AuthActivity::class.java))
//        }
//
//        val placeButton = root.findViewById(R.id.place_button) as Button
//        placeButton.setOnClickListener{
//            startActivity(Intent(context, PlaceActivity::class.java))
//        }

        return null
    }

}