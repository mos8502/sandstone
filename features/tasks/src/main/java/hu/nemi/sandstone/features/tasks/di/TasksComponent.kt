package hu.nemi.sandstone.features.tasks.di

import androidx.fragment.app.Fragment
import dagger.Component
import hu.nemi.sandstone.app.di.RootComponent
import javax.inject.Provider
import javax.inject.Scope

@Scope
annotation class TasksScope

@TasksScope
@Component(
    dependencies = [
        RootComponent::class
    ],
    modules = [
        TasksModule::class
    ])
interface TasksComponent {
    val fragmentProviders: Map<String, Provider<Fragment>>

    @Component.Factory
    interface Factory {
        operator fun invoke(rootComponent: RootComponent): TasksComponent
    }

    companion object:
        Factory {
        override fun invoke(rootComponent: RootComponent): TasksComponent =
            (DaggerTasksComponent.factory())(rootComponent)
    }
}