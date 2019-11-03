package hu.nemi.sandstone.app.di

import android.app.Application
import com.apollographql.apollo.ApolloClient
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import hu.nemi.sandstone.app.SandStoneApp
import hu.nemi.sandstone.app.db.SandstoneDb
import hu.nemi.sandstone.features.di.FeaturesModule
import hu.nemi.sandstone.login.di.GoogleSignInModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UiModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        FirebaseModule::class,
        GoogleSignInModule::class,
        FeaturesModule::class
    ]
)
interface RootComponent {
    val db: SandstoneDb
    val apolloClient: ApolloClient

    fun inject(sandStoneApp: SandStoneApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): RootComponent
    }
}
