package io.github.dmitrikudrenko.kebab.ui.shop.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.shop.view.KebabShopView
import javax.inject.Inject


class KebabShopPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopPresenter {
    private var id: Long = 0

    override fun init(view: KebabShopView) {
        kebabShopDataController.getKebabShop(id)
                .subscribe(
                        { view.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    override fun setShopId(id: Long) {
        this.id = id
    }
}