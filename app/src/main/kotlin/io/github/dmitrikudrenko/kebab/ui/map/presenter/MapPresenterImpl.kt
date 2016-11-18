package io.github.dmitrikudrenko.kebab.ui.map.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.map.MapContract
import rx.Subscription


class MapPresenterImpl constructor(val kebabShopDataController: IKebabShopDataController) : MapContract.MapPresenter {
    private var view: MapContract.MapView? = null
    private var dataSubscription: Subscription? = null

    override fun subscribe(view: MapContract.MapView) {
        this.view = view
    }

    override fun unsubscribe() {
        view = null
    }

    override fun onStart() {
        dataSubscription = kebabShopDataController.getKebabShops()
                .subscribe(
                        { view?.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    override fun onStop() {
        dataSubscription?.unsubscribe()
        dataSubscription = null
    }
}