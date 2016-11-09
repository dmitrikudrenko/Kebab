package io.github.dmitrikudrenko.kebab.ui.shop.presenter

import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.ui.shop.view.KebabShopView


interface KebabShopPresenter: IPresenter<KebabShopView> {
    fun setShopId(id: Long)
}