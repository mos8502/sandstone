package hu.nemi.sandstone.app.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import hu.nemi.sandstone.app.db.SandstoneDb
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    @JvmStatic
    fun database(context: Application): SandstoneDb =
        Room.databaseBuilder(context, SandstoneDb::class.java, "sandstone").build()
}