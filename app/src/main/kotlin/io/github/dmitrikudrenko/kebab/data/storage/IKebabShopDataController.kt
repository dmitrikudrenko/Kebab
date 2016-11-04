package io.github.dmitrikudrenko.kebab.data.storage

import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import rx.Observable

interface IKebabShopDataController {
    fun getKebabShops(): Observable<List<IKebabShop>>
}