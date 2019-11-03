package hu.nemi.sandstone.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.MapKey
import hu.nemi.sandstone.features.Feature
import javax.inject.Inject
import javax.inject.Provider

@MapKey
annotation class FragmentKey(val name: String)

class DispatchingFragmentFactory @Inject constructor(
    private val providers: FragmentFactoryMap,
    private val features: Set<@JvmSuppressWildcards Feature>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        providers[className]?.get()
            ?: features.asSequence()
                .mapNotNull { feature -> feature.createFragment(className) }
                .firstOrNull()
            ?: super.instantiate(classLoader, className)
}

typealias FragmentFactoryMap = Map<String, @JvmSuppressWildcards Provider<Fragment>>
