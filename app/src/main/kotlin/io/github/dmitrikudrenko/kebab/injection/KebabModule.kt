package io.github.dmitrikudrenko.kebab.injection

import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.analytics.AnalyticsReporter
import io.github.dmitrikudrenko.kebab.analytics.FirebaseAnalyticsReporter
import io.github.dmitrikudrenko.kebab.crash.CrashReporter
import io.github.dmitrikudrenko.kebab.crash.FirebaseCrashReporter
import io.github.dmitrikudrenko.kebab.data.internal.InternalSharedPreferences
import io.github.dmitrikudrenko.kebab.data.internal.PositionStorage
import io.github.dmitrikudrenko.kebab.data.model.factory.IKebabShopFactory
import io.github.dmitrikudrenko.kebab.data.model.factory.PojoKebabShopFactory
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.data.storage.MockKebabShopController
import javax.inject.Singleton

@Module
open class KebabModule(private val application: KebabApplication) {
    @Provides
    @Singleton
    fun provideApplication(): KebabApplication {
        return application
    }

    @Provides
    @Singleton
    fun provideKebabShopDataController() : IKebabShopDataController {
        return MockKebabShopController(application)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun providePositionStorage(sharedPreferences: SharedPreferences): PositionStorage {
        return InternalSharedPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCrashReporter(): CrashReporter {
        return FirebaseCrashReporter()
    }

    @Provides
    @Singleton
    fun provideAnalyticsReporter(): AnalyticsReporter {
        return FirebaseAnalyticsReporter(application)
    }

    @Provides
    @Singleton
    fun provideKebabShopFactory(): IKebabShopFactory {
        return PojoKebabShopFactory()
    }
}