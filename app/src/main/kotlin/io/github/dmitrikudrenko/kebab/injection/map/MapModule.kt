package io.github.dmitrikudrenko.kebab.injection.map

import dagger.Module
import dagger.Provides
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.map.presenter.MapPresenter
import io.github.dmitrikudrenko.kebab.ui.map.presenter.MapPresenterImpl
import javax.inject.Singleton

@Module
class MapModule {
    @Provides
    @Singleton
    fun provideMapPresenter(kebabShopDataController: IKebabShopDataController): MapPresenter {
        return MapPresenterImpl(kebabShopDataController)
    }
}