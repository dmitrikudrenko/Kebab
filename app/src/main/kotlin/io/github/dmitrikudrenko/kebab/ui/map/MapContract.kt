package io.github.dmitrikudrenko.kebab.ui.map

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.Contract


interface MapContract {
    interface MapView : Contract.IView {
        fun setData(data: List<IKebabShop>?)
    }

    interface MapPresenter : Contract.IPresenter<MapView> {
    }
}