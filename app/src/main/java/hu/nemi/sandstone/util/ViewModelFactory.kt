package hu.nemi.sandstone.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@MapKey
annotation class ViewModelKey(val type: KClass<out ViewModel>)

class ViewModelFactory @Inject constructor(private val viewModeProviderMap: ViewModelProviderMap) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        requireNotNull(viewModeProviderMap[modelClass]?.get()) as T
}

typealias ViewModelProviderMap = Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>