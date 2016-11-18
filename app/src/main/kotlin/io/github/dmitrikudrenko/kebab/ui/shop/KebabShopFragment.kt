package io.github.dmitrikudrenko.kebab.ui.shop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.ui.BaseFragment
import javax.inject.Inject


class KebabShopFragment : BaseFragment<KebabShopContract.KebabShopView, KebabShopContract.KebabShopPresenter>(),
        KebabShopContract.KebabShopView {
    @Inject
    override lateinit var presenter: KebabShopContract.KebabShopPresenter
    
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
        super.onViewCreated(view, savedInstanceState)
        presenter.setShopId(kebabShopId)
    }

    override fun setData(shop: IKebabShop?) {
        shop?.let {
            onShopLoadingListener?.onShopLoaded(it)
            shopNameView?.text = it.getName()
        }
    }
}