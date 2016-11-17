package io.github.dmitrikudrenko.kebab.ui.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.dmitrikudrenko.kebab.BR
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.ui.ListPresenter
import rx.Observable
import java.util.*

class KebabShopsAdapter(val presenter: ListPresenter<IKebabShop>) : RecyclerView.Adapter<KebabShopsAdapter.KebabShopViewHolder>() {
    private val listeners = ArrayList<OnShopClickListener>()

    init {
        setHasStableIds(presenter.hasStableIds())
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            KebabShopViewHolder(View.inflate(parent?.context, R.layout.v_item_kebab_shop, null))

    override fun onBindViewHolder(holder: KebabShopViewHolder?, position: Int) {
        val kebabShop = presenter.getItem(position)
        val view = holder?.bind(kebabShop)
        view?.setOnClickListener { dispatchShopClick(kebabShop) }
    }

    private fun dispatchShopClick(kebabShop: IKebabShop) {
        for (listener in listeners) {
            listener.onShopClicked(kebabShop)
        }
    }

    override fun getItemCount() = presenter.getCount()

    override fun getItemId(position: Int) = presenter.getItemId(position)

    class KebabShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewDataBinding

        init {
            binding = DataBindingUtil.bind(view)
        }

        fun bind(kebabShop: IKebabShop): View {
            binding.setVariable(BR.shop, kebabShop)
            return binding.root
        }
    }

    private fun registerClickListener(onShopClickListener: OnShopClickListener) {
        listeners.add(onShopClickListener)
    }

    private fun unregisterClickListener(onShopClickListener: OnShopClickListener) {
        listeners.remove(onShopClickListener)
    }

    fun getShopClickListener(): Observable<IKebabShop> {
        return Observable.create<IKebabShop> { subscriber ->
            val listener = object : OnShopClickListener {
                override fun onShopClicked(kebabShop: IKebabShop) {
                    if (subscriber.isUnsubscribed)
                        unregisterClickListener(this)
                    else subscriber.onNext(kebabShop)
                }
            }
            registerClickListener(listener)
        }
    }

    private interface OnShopClickListener {
        fun onShopClicked(kebabShop: IKebabShop)
    }
}