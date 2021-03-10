package com.example.myapplication.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.auth.LoginActivity

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
        var btn_new_activity = root.findViewById(R.id.register_button) as Button
        btn_new_activity.setOnClickListener{

            startActivity(Intent(context, AuthActivity::class.java))
         }

        var btn_new_activity2 = root.findViewById(R.id.login_button) as Button
        btn_new_activity2.setOnClickListener{

            startActivity(Intent(context, LoginActivity::class.java))
        }



        return root
    }

}