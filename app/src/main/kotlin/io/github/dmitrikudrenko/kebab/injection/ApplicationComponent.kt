package io.github.dmitrikudrenko.kebab.injection

import dagger.Component
import io.github.dmitrikudrenko.kebab.injection.auth.AuthModule
import io.github.dmitrikudrenko.kebab.ui.auth.SplashFragment
import io.github.dmitrikudrenko.kebab.ui.list.KebabShopsListFragment
import io.github.dmitrikudrenko.kebab.ui.map.KebabShopsMapActivity
import io.github.dmitrikudrenko.kebab.ui.map.KebabShopsMapFragment
import io.github.dmitrikudrenko.kebab.ui.shop.KebabShopFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(KebabModule::class, AuthModule::class))
interface ApplicationComponent {
    fun inject(fragment: KebabShopsListFragment)
    fun inject(fragment: KebabShopsMapFragment)
    fun inject(activity: KebabShopsMapActivity)
    fun inject(fragment: KebabShopFragment)
    fun inject(fragment: SplashFragment)
}