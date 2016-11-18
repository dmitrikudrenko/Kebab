package io.github.dmitrikudrenko.kebab.ui.map

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.mvp.IView


interface MapContract {
    interface MapView : IView {
        fun setData(data: List<IKebabShop>?)
    }

    interface MapPresenter : IPresenter<MapView> {
    }
}