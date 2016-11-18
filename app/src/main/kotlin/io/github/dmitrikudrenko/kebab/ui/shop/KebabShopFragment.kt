package io.github.dmitrikudrenko.kebab.ui.shop

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import javax.inject.Inject


class KebabShopFragment : Fragment(), KebebShopContract.KebabShopView {
    @Inject
    lateinit var presenter: KebebShopContract.KebabShopPresenter

    private var kebabShopId: Long = 0
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
        kebabShopId = arguments.getLong(KebabShopActivity.KEY_ID)
        return view
    }

    private fun injectViews(view: View?) {
        shopNameView = view?.findViewById(R.id.name) as TextView?
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.setShopId(kebabShopId)
        presenter.subscribe(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun setData(shop: IKebabShop?) {
        shop?.let {
            onShopLoadingListener?.onShopLoaded(it)
            shopNameView?.text = it.getName()
        }
    }
}