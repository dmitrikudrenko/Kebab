package io.github.dmitrikudrenko.kebab.ui.list.view

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.mvp.IView


interface KebabShopsView: IView {
    fun setData(data: List<IKebabShop>?)
    fun openShop(kebabShop: IKebabShop)
}