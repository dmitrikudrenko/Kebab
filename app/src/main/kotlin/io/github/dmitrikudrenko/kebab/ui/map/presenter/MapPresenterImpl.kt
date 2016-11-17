package io.github.dmitrikudrenko.kebab.ui.map.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.map.view.MapView
import rx.Subscription
import javax.inject.Inject


class MapPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : MapPresenter {
    private lateinit var view: MapView
    private var dataSubscription: Subscription? = null

    override fun onCreate(view: MapView) {
        this.view = view
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