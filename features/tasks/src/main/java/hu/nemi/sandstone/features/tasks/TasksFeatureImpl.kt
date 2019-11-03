package hu.nemi.sandstone.features.tasks

import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import hu.nemi.sandstone.app.di.RootComponent
import hu.nemi.sandstone.features.tasks.di.TasksComponent

/**
 * Actual [TasksFeature] implementation that should live inside a feature module
 */
class TasksFeatureImpl(rootComponent: RootComponent) : TasksFeature {
    private val component = TasksComponent(rootComponent)

    override fun createFragment(className: String): Fragment? =
        component.fragmentProviders[className]?.get()

    @AutoService(TasksFeature.Factory::class)
    class Factory : TasksFeature.Factory {
        override fun create(rootComponent: RootComponent): TasksFeature =
            TasksFeatureImpl(rootComponent)
    }
}
