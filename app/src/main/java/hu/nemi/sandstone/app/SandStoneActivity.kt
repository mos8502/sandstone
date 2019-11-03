package hu.nemi.sandstone.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import hu.nemi.sandstone.R
import hu.nemi.sandstone.util.Lce
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class SandStoneActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var rootViewModel: RootViewModel
    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        rootViewModel.requiredUpdate.observe(this, Observer { appUpdateInfo ->
            if (appUpdateInfo != null) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    rqRequiredAppUpdate
                )
            }
        })

        rootViewModel.state.observe(this, Observer { applicationState ->
            when (applicationState.account) {
                Lce.Loading -> {
                    findViewById<View>(R.id.navHost).isVisible = false
                    loading.isVisible = true
                }
                is Lce.Error -> finish() // TODO: show something meaningful instead
                is Lce.Content -> onAccountLoaded(applicationState.account.value)
            }
        })
    }

    private fun onAccountLoaded(value: FirebaseUser?) {
        loading.isVisible = false
        if (value == null) {
            onLogout()
        } else {
            onLogin()
        }
        findViewById<View>(R.id.navHost).isVisible = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            rqRequiredAppUpdate -> onRequiredUpdateResult(resultCode)
        }
    }

    @Inject
    fun inject(
        viewModelFactory: ViewModelProvider.Factory,
        fragmentFactory: FragmentFactory,
        appUpdateManager: AppUpdateManager) {
        this.appUpdateManager = appUpdateManager

        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)

        rootViewModel = viewModelProvider.get()
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    private fun onRequiredUpdateResult(resultCode: Int) {
        if (resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    private fun onLogout() = with(navHost.findNavController()) {
        if (currentDestination?.id != R.id.login) {
            navigate(R.id.login)
        }
    }

    private fun onLogin() = with(navHost.findNavController()) {
        if (currentDestination?.id == R.id.login) {
            navigate(R.id.tasks, null, NavOptions.Builder().setPopUpTo(R.id.login, true).build())
        }
    }

    private companion object {
        private const val rqRequiredAppUpdate = 0x1
    }
}
