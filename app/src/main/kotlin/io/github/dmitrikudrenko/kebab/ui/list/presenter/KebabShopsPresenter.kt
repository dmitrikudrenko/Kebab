package io.github.dmitrikudrenko.kebab.ui.list.presenter

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.ui.list.view.KebabShopsView


interface KebabShopsPresenter: IPresenter<KebabShopsView> {
    fun onShopClick(kebabShop: IKebabShop)
}