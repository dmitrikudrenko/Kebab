package io.github.dmitrikudrenko.kebab.ui.shop

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop


interface OnShopLoadingListener {
    fun onShopLoaded(shop: IKebabShop)
}