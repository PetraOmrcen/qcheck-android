package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.network.AuthApi
import com.example.myapplication.data.network.RemoteDataSource
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.ui.base.ViewModelFactory
import com.example.myapplication.ui.startNewActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


class AuthActivity : AppCompatActivity() {

    val remoteDataSource = RemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun googleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 9000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            //startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken
            val userPreferences = UserPreferences(this)
            val repository = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
            val factory = ViewModelFactory(repository)
            val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
            if (idToken != null) {
                viewModel.google_login(idToken)
            }

            viewModel.loginResponse.observeForever {
                when (it){
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            viewModel.saveAuthToken(it.value.access_token)
                            startNewActivity(MainActivity::class.java)
                        }
                    }
                    is Resource.Failure -> print("Error!")
                }
            }

        } catch (e: ApiException) {
            print(e.message)
        }
    }
}