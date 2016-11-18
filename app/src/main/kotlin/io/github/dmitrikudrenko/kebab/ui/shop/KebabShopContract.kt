package io.github.dmitrikudrenko.kebab.ui.shop

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.Contract


interface KebabShopContract {
    interface KebabShopView: Contract.IView {
        fun setData(shop: IKebabShop?)
    }

    interface KebabShopPresenter: Contract.IPresenter<KebabShopView> {
        fun setShopId(id: Long)
    }
}