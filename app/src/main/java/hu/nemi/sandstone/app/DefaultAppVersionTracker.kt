package hu.nemi.sandstone.app

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class DefaultAppVersionTracker(private val preferences: SharedPreferences) : AppVersionTracker {

    @Inject
    constructor(context: Application) : this(PreferenceManager.getDefaultSharedPreferences(context))

    override var currentVersion: Int
        get() = preferences.getInt(appVersion, AppVersionTracker.versionNotAvailable)
        set(value) {
            preferences.edit(true) { putInt(appVersion, value) }
        }

    private companion object {
        const val appVersion = "app_version"
    }
}