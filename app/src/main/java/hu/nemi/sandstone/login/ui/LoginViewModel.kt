package hu.nemi.sandstone.login.ui

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import hu.nemi.sandstone.login.network.LoginService
import hu.nemi.sandstone.util.await
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    object LoggedOut : LoginState()
    object LoginInProgress : LoginState()
    object LoggedIn : LoginState()
    object LoginError : LoginState()
}

class LoginViewModel @Inject constructor(
    private val fbAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val loginService: LoginService
) : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>(LoginState.LoggedOut)

    val signInIntent: Intent
        get() = googleSignInClient.signInIntent

    val loginState: LiveData<LoginState> = _loginState

    fun onSignInResult(data: Intent?) {
        login(GoogleSignIn.getSignedInAccountFromIntent(data))
    }

    private fun login(account: Task<GoogleSignInAccount>) {
        viewModelScope.launch {
            _loginState.value =
                LoginState.LoginInProgress
            try {
                val user = performLogin(account)
                Crashlytics.setString("user_uid", user.uid)
                Crashlytics.log(Log.INFO, LOG_TAG, "login successful")
            } catch (loginError: Throwable) {
                Crashlytics.log(Log.ERROR, LOG_TAG, "login failed")
                Crashlytics.logException(loginError)
                _loginState.value =
                    LoginState.LoginError
            }

        }
    }

    private suspend fun performLogin(account: Task<GoogleSignInAccount>) = coroutineScope {
        val idToken = loginService.login(requireNotNull(account.await().serverAuthCode) {
            "serverAuthCode cannot be null"
        })
        val authResult =
            fbAuth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                .await()
        val user = requireNotNull(authResult?.user) {
            "user cannot be null if sign in was successful"
        }
        user
    }
}


private const val LOG_TAG = "Login"