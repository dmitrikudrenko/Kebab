package io.github.dmitrikudrenko.kebab.ui.list

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.Contract


interface KebabShopsContract {
    interface KebabShopsView : Contract.IView {
        fun setData(data: List<IKebabShop>?)
        fun openShop(kebabShop: IKebabShop)
    }

    interface KebabShopsPresenter : Contract.IPresenter<KebabShopsView> {
        fun onShopClick(kebabShop: IKebabShop)
    }
}