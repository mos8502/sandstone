package hu.nemi.sandstone.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import hu.nemi.sandstone.BuildConfig
import hu.nemi.sandstone.util.Lce
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val fbAuth: FirebaseAuth,
    private val fbRemoteConfig: FirebaseRemoteConfig,
    private val appUpdateManager: AppUpdateManager,
    private val appVersionTracker: AppVersionTracker
) : ViewModel(), FirebaseAuth.AuthStateListener {
    private val isUpdateRequired: Boolean
        get() = fbRemoteConfig.getBoolean("app_update_required")

    private val isAppUpdated: Boolean
        get() = appVersionTracker.currentVersion != BuildConfig.VERSION_CODE

    private val fetchConfigs: Task<Void>

    val state = MutableLiveData<ApplicationState>(ApplicationState(Lce.Loading))

    val requiredUpdate: LiveData<AppUpdateInfo>

    init {
        // fetch configs
        fetchConfigs = if (isAppUpdated) {
            // force fetch configs
            fbRemoteConfig.fetch(0)
        } else {
            // fetch configs if needed
            fbRemoteConfig.fetch()
        }

        // start listening to auth state changes after config fetch completed
        fetchConfigs.addOnCompleteListener {
            // make sure to activate configs if fetched
            fbRemoteConfig.activate()
            // save current version to prevent force fetch on next start
            appVersionTracker.currentVersion = BuildConfig.VERSION_CODE
            // start listening to auth state changes
            fbAuth.addAuthStateListener(::onAuthStateChanged)
        }

        requiredUpdate = object : LiveData<AppUpdateInfo>() {
            override fun onActive() {
                fetchConfigs.addOnCompleteListener {
                    Log.i(LOG_TAG, "configs fetched, get AppUpdateInfo")
                    appUpdateManager.appUpdateInfo.addOnCompleteListener { task ->
                        Log.i(
                            LOG_TAG, """
                            configs fetched, getAppUpdateInfo completed.
                            isSuccessful: ${task.isSuccessful}
                            isUpdateRequired: $isUpdateRequired
                            updateAvailability ${if (task.isSuccessful) task.result.updateAvailability() else 0}
                            isImmediateUpdateAllowed: ${if (task.isSuccessful) task.result.isUpdateTypeAllowed(
                                AppUpdateType.IMMEDIATE
                            ) else false}
                            """.trimIndent()
                        )
                        if (task.isSuccessful &&
                            isUpdateRequired &&
                            task.result.updateAvailability() in setOf(
                                UPDATE_AVAILABLE,
                                DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                            ) &&
                            task.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                        ) {
                            value = task.result
                        }
                    }
                }
            }
        }
    }

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        state.value = state.value?.copy(account = Lce.Content(auth.currentUser))
    }

    override fun onCleared() {
        fbAuth.removeAuthStateListener(this)
    }
}

private const val LOG_TAG = "RootViewModel"