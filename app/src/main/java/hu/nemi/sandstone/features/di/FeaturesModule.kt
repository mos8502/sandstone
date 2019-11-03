package hu.nemi.sandstone.features.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import hu.nemi.sandstone.features.Feature
import hu.nemi.sandstone.features.tasks.TasksFeatureWrapper
import javax.inject.Singleton

@Module
object FeaturesModule {
    @JvmStatic
    @Provides
    @Singleton
    @IntoSet
    fun tasksFeature(feature: TasksFeatureWrapper): Feature = feature
}