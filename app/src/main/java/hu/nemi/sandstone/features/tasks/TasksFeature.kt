package hu.nemi.sandstone.features.tasks

import hu.nemi.sandstone.app.di.RootComponent
import hu.nemi.sandstone.features.Feature

interface TasksFeature: Feature {
    interface Factory {
        fun create(rootComponent: RootComponent): TasksFeature
    }
}
