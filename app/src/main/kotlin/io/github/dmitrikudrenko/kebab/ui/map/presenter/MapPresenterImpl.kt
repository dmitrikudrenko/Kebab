package io.github.dmitrikudrenko.kebab.ui.map.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.map.view.MapView
import javax.inject.Inject


class MapPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : MapPresenter {
    override fun init(view: MapView) {
        kebabShopDataController.getKebabShops()
                .subscribe(
                        { view.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }
}