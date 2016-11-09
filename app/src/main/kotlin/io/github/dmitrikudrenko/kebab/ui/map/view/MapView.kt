package io.github.dmitrikudrenko.kebab.ui.map.view

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IView


interface MapView: IView {
    fun setData(data: List<IKebabShop>?)
}