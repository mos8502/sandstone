package hu.nemi.sandstone.app

interface AppVersionTracker {
    var currentVersion: Int

    companion object {
        const val versionNotAvailable = -1
    }
}