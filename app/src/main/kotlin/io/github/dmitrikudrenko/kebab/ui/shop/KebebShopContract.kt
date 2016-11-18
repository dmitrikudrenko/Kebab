package io.github.dmitrikudrenko.kebab.ui.shop

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.mvp.IView


interface KebebShopContract {
    interface KebabShopView: IView {
        fun setData(shop: IKebabShop?)
    }

    interface KebabShopPresenter: IPresenter<KebabShopView> {
        fun setShopId(id: Long)
    }
}