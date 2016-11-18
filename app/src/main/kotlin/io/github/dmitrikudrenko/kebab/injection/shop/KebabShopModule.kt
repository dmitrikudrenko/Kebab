package io.github.dmitrikudrenko.kebab.injection.shop

import dagger.Module
import dagger.Provides
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.shop.KebabShopContract
import io.github.dmitrikudrenko.kebab.ui.shop.presenter.KebabShopPresenterImpl
import javax.inject.Singleton

@Module
class KebabShopModule {
    @Provides
    @Singleton
    fun provideKebabShopPresenter(kebabShopDataController: IKebabShopDataController): KebabShopContract.KebabShopPresenter {
        return KebabShopPresenterImpl(kebabShopDataController)
    }
}