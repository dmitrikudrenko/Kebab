package io.github.dmitrikudrenko.kebab

import android.app.Application
import io.github.dmitrikudrenko.kebab.injection.ApplicationComponent
import io.github.dmitrikudrenko.kebab.injection.DaggerApplicationComponent
import io.github.dmitrikudrenko.kebab.injection.KebabModule

class KebabApplication: Application() {
    companion object {
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().kebabModule(KebabModule(this)).build()
    }
}