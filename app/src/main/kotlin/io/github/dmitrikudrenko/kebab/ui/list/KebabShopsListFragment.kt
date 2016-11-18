package io.github.dmitrikudrenko.kebab.ui.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.databinding.FKebabShopsListBinding
import io.github.dmitrikudrenko.kebab.ui.shop.KebabShopActivity
import rx.Subscription
import javax.inject.Inject

class KebabShopsListFragment : Fragment(), KebabShopsContract.KebabShopsView {
    @Inject
    lateinit var presenter: KebabShopsContract.KebabShopsPresenter

    private var binding: FKebabShopsListBinding? = null
    private val listPresenter = KebabListPresenter()
    private var clickSubscription: Subscription? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_kebab_shops_list, container, false)
        KebabApplication.graph.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = KebabShopsAdapter(listPresenter)
        binding?.recyclerView?.adapter = adapter
        clickSubscription = adapter.getShopClickListener().subscribe(
                { presenter.onShopClick(it) },
                { Log.e("Shop clicked", it.message, it) }
        )
        presenter.subscribe(this)
    }

    override fun onDestroyView() {
        clickSubscription?.unsubscribe()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun setData(data: List<IKebabShop>?) {
        data?.let { listPresenter.onDataChanged(it) }
    }

    override fun openShop(kebabShop: IKebabShop) {
        KebabShopActivity.startActivity(activity, kebabShop)
    }
}