package io.github.dmitrikudrenko.kebab.ui.list

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.ui.ListPresenter

class KebabListPresenter : ListPresenter<IKebabShop>() {
    override fun hasStableIds() = true

    override fun getItemId(position: Int) = getItem(position).getId()
}