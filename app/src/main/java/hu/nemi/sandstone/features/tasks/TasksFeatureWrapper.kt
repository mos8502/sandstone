package hu.nemi.sandstone.features.tasks

import androidx.fragment.app.Fragment
import hu.nemi.sandstone.app.di.RootComponent
import java.util.*
import javax.inject.Inject

class TasksFeatureWrapper @Inject constructor(private val rootComponent: RootComponent) :
    TasksFeature {
    private var feature: TasksFeature? = loadFeature(reload = false)

    override fun createFragment(className: String): Fragment? =
        getFeature()?.createFragment(className)

    private fun getFeature(): TasksFeature? {
        if (feature == null) {
            feature = loadFeature(true)
        }

        return feature
    }

    private fun loadFeature(reload: Boolean): TasksFeature? {
        if (reload) {
            serviceLoader.reload()
        }

        return serviceLoader.iterator()
            .asSequence()
            .firstOrNull()
            ?.create(rootComponent)
    }

    private companion object {
        private val serviceLoader = ServiceLoader.load(TasksFeature.Factory::class.java)
    }
}