package hu.nemi.sandstone.app.di

import android.app.Application
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides

@Module
object FirebaseModule {
    @JvmStatic
    @get:Provides
    val firebaseAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    @JvmStatic
    @get:Provides
    val firebaseRemoteConfig: FirebaseRemoteConfig
        get() = FirebaseRemoteConfig.getInstance()

    @JvmStatic
    @Provides
    fun appUpdateManager(application: Application): AppUpdateManager =
        AppUpdateManagerFactory.create(application)
}