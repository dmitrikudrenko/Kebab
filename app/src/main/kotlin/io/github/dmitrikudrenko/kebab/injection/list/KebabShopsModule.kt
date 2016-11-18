package io.github.dmitrikudrenko.kebab.injection.list

import dagger.Module
import dagger.Provides
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.list.KebabShopsContract
import io.github.dmitrikudrenko.kebab.ui.list.presenter.KebabShopsPresenterImpl
import javax.inject.Singleton

@Module
class KebabShopsModule {
    @Provides
    @Singleton
    fun provideKebabShopsPresenter(kebabShopDataController: IKebabShopDataController): KebabShopsContract.KebabShopsPresenter {
        return KebabShopsPresenterImpl(kebabShopDataController)
    }
}