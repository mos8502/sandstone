package hu.nemi.sandstone.app

import com.facebook.stetho.Stetho
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import hu.nemi.sandstone.app.di.DaggerRootComponent
import javax.inject.Inject

class SandStoneApp : SplitCompatApplication(), HasAndroidInjector {
    private val rootComponent by lazy {
        DaggerRootComponent.factory().create(this)
    }
    private lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        rootComponent.inject(this)
    }

    @Inject
    fun inject(androidInjector: DispatchingAndroidInjector<Any>) {
        this.androidInjector = androidInjector
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}