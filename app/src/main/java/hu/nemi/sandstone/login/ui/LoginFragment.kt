package hu.nemi.sandstone.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.nemi.sandstone.R
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment @Inject constructor(viewModelFactory: ViewModelProvider.Factory) : Fragment() {
    private val loginViewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer { state ->
            signInButton.isEnabled = state in setOf(
                LoginState.LoggedOut,
                LoginState.LoginError
            )
        })

        signInButton.setOnClickListener {
            startActivityForResult(loginViewModel.signInIntent,
                RQ_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_SIGN_IN) {
            loginViewModel.onSignInResult(data)

        }
    }
}

private const val RQ_SIGN_IN = 0x1