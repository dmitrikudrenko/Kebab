package io.github.dmitrikudrenko.kebab.ui.shop.view

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IView


interface KebabShopView: IView {
    fun setData(shop: IKebabShop?)
}