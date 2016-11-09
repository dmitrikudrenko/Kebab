package io.github.dmitrikudrenko.kebab.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.ui.list.presenter.KebabShopsPresenter
import io.github.dmitrikudrenko.kebab.ui.list.view.KebabShopsView
import javax.inject.Inject

class KebabShopsListFragment : Fragment(), KebabShopsView {
    @Inject
    lateinit var presenter: KebabShopsPresenter

    private var recyclerView: RecyclerView? = null
    private val listPresenter = KebabListPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.f_kebab_shops_list, container, false)
        injectViews(view)
        KebabApplication.graph.inject(this)
        return view
    }

    private fun injectViews(view: View?) {
        recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = KebabShopsAdapter(listPresenter)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.init(this)
    }

    override fun setData(data: List<IKebabShop>) {
        listPresenter.onDataChanged(data)
    }
}