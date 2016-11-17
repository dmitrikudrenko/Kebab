package io.github.dmitrikudrenko.kebab.data.model.factory

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop

interface IKebabShopFactory {
    fun create(id: Long, name: String?, address: String?, latitude: Double?, longitude: Double?,
               rating: Double?, description: String?, image: String?): IKebabShop
}