package com.example.myapplication.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.example.myapplication.data.network.Resource
import com.example.myapplication.data.responses.User
import com.example.myapplication.ui.auth.LoginFragment
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun makeUserFromJWT(jwt: JWT): User {
    val email = jwt.getClaim("user_name").asString()
    val firstName = jwt.getClaim("firstName").asString()
    val lastName = jwt.getClaim("lastName").asString()
    val id = jwt.getClaim("id").asString()
    val roleId = jwt.getClaim("authorities").asArray(String::class.java)[0]
    val user = User(null, null, email.toString(), id.toString().toInt(), firstName.toString(), lastName.toString(), roleId.toString() )
    return user
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}
