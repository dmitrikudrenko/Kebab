package io.github.dmitrikudrenko.kebab.data.model.factory

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.model.KebabShop

class PojoKebabShopFactory : IKebabShopFactory {
    override fun create(id: Long, name: String?, address: String?, latitude: Double?, longitude: Double?,
                        rating: Double?, description: String?, image: String?): IKebabShop {
        return KebabShop(id, name, address, latitude, longitude, rating, description, image)
    }
}