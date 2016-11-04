package io.github.dmitrikudrenko.kebab.ui.map

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop

interface OnShopSelectedListener {
    fun onShopSelected(shop: IKebabShop): Boolean
}