package io.github.dmitrikudrenko.kebab.ui.shop.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.shop.KebabShopContract
import rx.Subscription


class KebabShopPresenterImpl constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopContract.KebabShopPresenter {
    private var view: KebabShopContract.KebabShopView? = null
    private var id: Long = 0
    private var dataSubscription: Subscription? = null

    override fun subscribe(view: KebabShopContract.KebabShopView) {
        this.view = view
    }

    override fun unsubscribe() {
        view = null
    }

    override fun onStart() {
        dataSubscription = kebabShopDataController.getKebabShop(id)
                .subscribe(
                        { view?.setData(it) },
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