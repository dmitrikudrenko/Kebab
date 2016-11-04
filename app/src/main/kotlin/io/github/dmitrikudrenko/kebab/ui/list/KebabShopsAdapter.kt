package io.github.dmitrikudrenko.kebab.ui.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.dmitrikudrenko.kebab.BR
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import java.util.*

class KebabShopsAdapter : RecyclerView.Adapter<KebabShopsAdapter.KebabShopViewHolder>() {
    private var data: List<IKebabShop> = ArrayList<IKebabShop>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KebabShopViewHolder? {
        parent?.let {
            val view = View.inflate(parent.context, R.layout.v_item_kebab_shop, null)
            return KebabShopViewHolder(view)
        }
        return null
    }

    override fun onBindViewHolder(holder: KebabShopViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].getId()
    }

    fun notifyDataSetChanged(data: List<IKebabShop>) {
        this.data = data
        notifyDataSetChanged()
    }

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