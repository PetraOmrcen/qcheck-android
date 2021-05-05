package com.example.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.UserApi
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.base.ViewModelFactory
import com.example.myapplication.ui.disableEnableControls
import com.example.myapplication.ui.favourites.FavouritesActivity
import com.example.myapplication.ui.makeUserFromJWT
import com.example.myapplication.ui.myPlaces.MyPlacesActivity
import com.example.myapplication.ui.place.PlaceActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private lateinit var  authToken: String
    var googleAcc: Boolean = false
    lateinit var  account: GoogleSignInAccount

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        authToken = runBlocking { userPreferences.authToken.first() }.toString()

        if(GoogleSignIn.getLastSignedInAccount(context) != null){
            account = GoogleSignIn.getLastSignedInAccount(context)!!
            googleAcc = true
        }

        if(authToken == getString(R.string.NULL_STRING) && !googleAcc) {
            startActivity(Intent(context, AuthActivity::class.java))
        }

        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(authToken != getString(R.string.NULL_STRING)) {
            val token = authToken
            val jwt = JWT(token)
            var user = makeUserFromJWT(jwt)
            binding.textViewProfile.text =  user.firstName + " " + user.lastName
            if(user.roleId == getString(R.string.ROLE_USER)){
                disableEnableControls(false, binding.linearlayout2)
                binding.linearlayout3.setOnClickListener{
                    Global.fragmentStack.add(R.id.navigation_profile)
                    val intent = Intent(context, FavouritesActivity::class.java)
                    startActivity(intent)
                }
            }else if(user.roleId == getString(R.string.ROLE_OWNER)){
                binding.linearlayout2.setOnClickListener{
                    Global.fragmentStack.add(R.id.navigation_profile)
                    val intent = Intent(context, MyPlacesActivity::class.java)
                    startActivity(intent)
                }
                binding.linearlayout3.setOnClickListener{
                    Global.fragmentStack.add(R.id.navigation_profile)
                    val intent = Intent(context, FavouritesActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        if(googleAcc) {
            binding.textViewProfile.text =  account.givenName + " " + account.familyName
        }

        binding.logoutText.setOnClickListener {
            logout()
        }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
            UserRepository(remoteDataSource.buildApi(UserApi::class.java))

}