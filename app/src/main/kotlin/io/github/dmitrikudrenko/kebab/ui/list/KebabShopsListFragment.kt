package io.github.dmitrikudrenko.kebab.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import javax.inject.Inject

class KebabShopsListFragment : Fragment() {
    @Inject
    lateinit var kebabShopDataController: IKebabShopDataController

    private var recyclerView: RecyclerView? = null
    private var kebabShopsAdapter: KebabShopsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.f_kebab_shops_list, container)
        recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(context)
        kebabShopsAdapter = KebabShopsAdapter()
        recyclerView?.adapter = kebabShopsAdapter
        KebabApplication.graph.inject(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kebabShopDataController.getKebabShops()
                .subscribe(
                        { kebabShopsAdapter?.notifyDataSetChanged(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }
}