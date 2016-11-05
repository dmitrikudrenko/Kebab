package io.github.dmitrikudrenko.kebab.ui.shop

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import javax.inject.Inject


class KebabShopFragment : Fragment() {
    @Inject
    lateinit var kebabShopDataController: IKebabShopDataController

    private var kebabShopId: Long? = null
    private var onShopLoadingListener: OnShopLoadingListener? = null

    private var shopNameView: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnShopLoadingListener)
            onShopLoadingListener = context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.f_kebab_shop, container, false)
        injectViews(view)
        KebabApplication.graph.inject(this)
        kebabShopId = arguments.getLong("id")
        return view
    }

    private fun injectViews(view: View?) {
        shopNameView = view?.findViewById(R.id.name) as TextView?
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kebabShopId?.let {
            kebabShopDataController.getKebabShop(it)
                    .subscribe(
                            { setData(it) },
                            { Log.e("Data loading", it.message, it) }
                    )
        }
    }

    private fun setData(shop: IKebabShop?) {
        shop?.let {
            onShopLoadingListener?.onShopLoaded(it)
            shopNameView?.text = it.getName()
        }
    }
}