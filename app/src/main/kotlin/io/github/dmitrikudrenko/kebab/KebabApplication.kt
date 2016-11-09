package io.github.dmitrikudrenko.kebab

import android.app.Application
import io.github.dmitrikudrenko.kebab.crash.CrashReporter
import io.github.dmitrikudrenko.kebab.injection.ApplicationComponent
import io.github.dmitrikudrenko.kebab.injection.DaggerApplicationComponent
import io.github.dmitrikudrenko.kebab.injection.KebabModule
import io.github.dmitrikudrenko.kebab.injection.auth.AuthModule
import javax.inject.Inject

class KebabApplication: Application() {
    @Inject
    lateinit var crashReporter: CrashReporter

    companion object {
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder()
                .kebabModule(KebabModule(this))
                .authModule(AuthModule())
                .build()
        graph.inject(this)
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable -> crashReporter.log(throwable) }
    }
}