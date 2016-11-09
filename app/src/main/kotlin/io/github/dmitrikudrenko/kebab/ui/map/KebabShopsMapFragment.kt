package io.github.dmitrikudrenko.kebab.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.internal.Position
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import javax.inject.Inject

class KebabShopsMapFragment : Fragment(), OnMapReadyCallback {
    @Inject
    lateinit var kebabShopDataController: IKebabShopDataController

    private var position: Position? = null

    private var mapFragment: SupportMapFragment? = null
    private var mapPresenter = MapPresenter()
    private var onShopSelectedListener: OnShopSelectedListener? = null
    private var onOutsideClickListener: OnOutsideClickListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnShopSelectedListener)
            onShopSelectedListener = context
        if (context is OnOutsideClickListener)
            onOutsideClickListener = context
    }

    override fun onCreateView(inflater: LayoutInflater?, parent: ViewGroup?, bundle: Bundle?): View? {
        KebabApplication.graph.inject(this)
        return inflater?.inflate(R.layout.f_kebab_shops_map, parent, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            mapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction()
                    .replace(R.id.content, mapFragment, TAG)
                    .commitNow()
        } else mapFragment = childFragmentManager.findFragmentByTag(TAG) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        mapPresenter.onMapReady(map, position)
        mapPresenter.setOnOutsideClickListener(onOutsideClickListener)
        mapPresenter.setOnShopSelectedListener(onShopSelectedListener)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            map?.isMyLocationEnabled = true
        loadData()
    }

    private fun loadData() {
        kebabShopDataController.getKebabShops()
                .subscribe(
                        { mapPresenter.presentShops(it) },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    fun getPosition(): Position? {
        return mapPresenter.getPosition()
    }

    fun setPosition(position: Position?) {
        this.position = position
    }

    override fun onDestroyView() {
        mapPresenter.onDestroy()
        super.onDestroyView()
    }

    companion object {
        val TAG = "SupportMapFragment"
    }
}