package io.github.dmitrikudrenko.kebab.ui.list.presenter

import android.util.Log
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import io.github.dmitrikudrenko.kebab.ui.list.KebabShopsContract
import rx.Subscription
import javax.inject.Inject


class KebabShopsPresenterImpl @Inject constructor(val kebabShopDataController: IKebabShopDataController) : KebabShopsContract.KebabShopsPresenter {
    private var view: KebabShopsContract.KebabShopsView? = null
    private var dataSubscription: Subscription? = null

    override fun subscribe(view: KebabShopsContract.KebabShopsView) {
        this.view = view
    }

    override fun unsubscribe() {
        view = null
    }

    override fun onShopClick(kebabShop: IKebabShop) {
        view?.openShop(kebabShop)
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