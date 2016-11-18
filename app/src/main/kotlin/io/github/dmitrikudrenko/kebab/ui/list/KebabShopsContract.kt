package io.github.dmitrikudrenko.kebab.ui.list

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.mvp.IView


interface KebabShopsContract {
    interface KebabShopsView : IView {
        fun setData(data: List<IKebabShop>?)
        fun openShop(kebabShop: IKebabShop)
    }

    interface KebabShopsPresenter : IPresenter<KebabShopsView> {
        fun onShopClick(kebabShop: IKebabShop)
    }
}