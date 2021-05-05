package com.example.myapplication.ui.base

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.myapplication.MainActivity
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.repository.BaseRepository
import com.example.myapplication.ui.startNewActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

    fun logout() = lifecycleScope.launch{
        val authToken = userPreferences.authToken.first()
        //val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        //viewModel.logout(api)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        var mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        runBlocking { mGoogleSignInClient.signOut() }

        userPreferences.clear()
        requireActivity().startNewActivity(MainActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}
