package io.github.dmitrikudrenko.kebab.ui.shop.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.shop.view.KebabShopView
import rx.Subscription
import javax.inject.Inject


class KebabShopPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopPresenter {
    private lateinit var view: KebabShopView
    private var id: Long = 0
    private var dataSubscription: Subscription? = null

    override fun onCreate(view: KebabShopView) {
        this.view = view
    }

    override fun onStart() {
        dataSubscription = kebabShopDataController.getKebabShop(id)
                .subscribe(
                        { view.setData(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    override fun onStop() {
        dataSubscription?.unsubscribe()
        dataSubscription = null
    }

    override fun setShopId(id: Long) {
        this.id = id
    }
}