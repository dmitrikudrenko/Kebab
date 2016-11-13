package io.github.dmitrikudrenko.kebab.ui.list.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.list.view.KebabShopsView
import rx.Subscription
import javax.inject.Inject


class KebabShopsPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopsPresenter {
    private lateinit var view: KebabShopsView
    private var dataSubscription: Subscription? = null

    override fun onCreate(view: KebabShopsView) {
        this.view = view
    }

    override fun onShopClick(kebabShop: IKebabShop) {
        view.openShop(kebabShop)
    }

    override fun onStart() {
        dataSubscription = kebabShopDataController.getKebabShops()
                .subscribe(
                        { view.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    override fun onStop() {
        dataSubscription?.unsubscribe()
        dataSubscription = null
    }
}