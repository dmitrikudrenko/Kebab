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

class KebabShopsAdapter(val presenter: ListPresenter<IKebabShop>) : RecyclerView.Adapter<KebabShopsAdapter.KebabShopViewHolder>() {
    init {
        setHasStableIds(presenter.hasStableIds())
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            KebabShopViewHolder(View.inflate(parent?.context, R.layout.v_item_kebab_shop, null))

    override fun onBindViewHolder(holder: KebabShopViewHolder?, position: Int) {
        holder?.bind(presenter.getItem(position))
    }

    override fun getItemCount() = presenter.getCount()

    override fun getItemId(position: Int) = presenter.getItemId(position)

    class KebabShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewDataBinding

        init {
            binding = DataBindingUtil.bind(view)
        }

        fun bind(kebabShop: IKebabShop) {
            binding.setVariable(BR.shop, kebabShop)
        }
    }
}