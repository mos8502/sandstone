package hu.nemi.sandstone.app.di

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import hu.nemi.sandstone.app.RootViewModel
import hu.nemi.sandstone.util.ViewModelKey

@Module
object RootModule {
    @Provides
    @IntoMap
    @ViewModelKey(RootViewModel::class)
    @JvmStatic
    fun rootViewModel(viewModel: RootViewModel): ViewModel = viewModel

}