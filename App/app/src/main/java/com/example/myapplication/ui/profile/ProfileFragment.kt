package com.example.myapplication.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.ui.home.HomeViewModel
import kotlinx.android.synthetic.*
import androidx.navigation.findNavController
import com.example.myapplication.ui.auth.AuthActivity

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        //val textView: TextView = root.findViewById(R.id.text_profile)
        //gumbic i activity
        var btn_new_activity = root.findViewById(R.id.btn_start_new_acitvity) as Button
        btn_new_activity.setOnClickListener{

            startActivity(Intent(context, AuthActivity::class.java))
         }



        return root
    }

}