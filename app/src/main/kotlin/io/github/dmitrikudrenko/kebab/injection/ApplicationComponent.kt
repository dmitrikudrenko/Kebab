package io.github.dmitrikudrenko.kebab.injection

import dagger.Component
import io.github.dmitrikudrenko.kebab.ui.list.KebabShopsListFragment
import io.github.dmitrikudrenko.kebab.ui.map.KebabShopsMapActivity
import io.github.dmitrikudrenko.kebab.ui.map.KebabShopsMapFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(KebabModule::class))
interface ApplicationComponent {
    fun inject(fragment: KebabShopsListFragment)
    fun inject(fragment: KebabShopsMapFragment)
    fun inject(activity: KebabShopsMapActivity)
}