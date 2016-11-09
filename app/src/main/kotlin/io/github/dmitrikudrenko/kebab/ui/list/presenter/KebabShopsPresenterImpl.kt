package io.github.dmitrikudrenko.kebab.ui.list.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.list.view.KebabShopsView
import javax.inject.Inject


class KebabShopsPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopsPresenter {
    override fun init(view: KebabShopsView) {
        kebabShopDataController.getKebabShops()
                .subscribe(
                        { view.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }
}