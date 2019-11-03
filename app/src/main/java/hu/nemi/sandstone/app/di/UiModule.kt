package hu.nemi.sandstone.app.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import hu.nemi.sandstone.app.AppVersionTracker
import hu.nemi.sandstone.app.DefaultAppVersionTracker
import hu.nemi.sandstone.app.RootViewModel
import hu.nemi.sandstone.app.SandStoneActivity
import hu.nemi.sandstone.login.ui.LoginFragment
import hu.nemi.sandstone.login.ui.LoginViewModel
import hu.nemi.sandstone.util.DispatchingFragmentFactory
import hu.nemi.sandstone.util.FragmentKey
import hu.nemi.sandstone.util.ViewModelFactory
import hu.nemi.sandstone.util.ViewModelKey

@Module
abstract class UiModule {
    @get:ContributesAndroidInjector
    abstract val sandStoneActivity: SandStoneActivity

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun fragmentFactory(factory: DispatchingFragmentFactory): FragmentFactory

    @Binds
    abstract fun appVersionTracker(instance: DefaultAppVersionTracker): AppVersionTracker

    @Binds
    @IntoMap
    @ViewModelKey(RootViewModel::class)
    abstract fun rootViewModel(viewModel: RootViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey("hu.nemi.sandstone.login.ui.LoginFragment")
    abstract fun loginFragment(fragment: LoginFragment): Fragment
}
