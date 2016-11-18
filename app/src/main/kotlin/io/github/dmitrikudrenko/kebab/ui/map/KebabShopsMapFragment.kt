package io.github.dmitrikudrenko.kebab.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.internal.Position
import io.github.dmitrikudrenko.kebab.data.internal.PositionStorage
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import javax.inject.Inject

class KebabShopsMapFragment : Fragment(), OnMapReadyCallback, MapContract.MapView, GoogleMap.OnMapLoadedCallback {
    @Inject
    lateinit var presenter: MapContract.MapPresenter

    @Inject
    lateinit var positionStorage: PositionStorage

    private var mapFragment: SupportMapFragment? = null

    private var map: GoogleMap? = null
    private val markers = ArrayMap<Marker, IKebabShop>()
    private var bounds: LatLngBounds.Builder? = null
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

    override fun onMapLoaded() {
        if (bounds != null)
            map?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds?.build(), 200))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction()
                    .replace(R.id.content, mapFragment, TAG)
                    .commitNow()
        } else mapFragment = childFragmentManager.findFragmentByTag(TAG) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        presenter.subscribe(this)
    }

    override fun onStop() {
        presenter.onStop()
        map?.setOnMapLoadedCallback(null)
        super.onStop()
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        presenter.onStart()
        map?.setOnMarkerClickListener {
            if (it != null) {
                val shop = markers[it]
                if (shop != null) {
                    map.animateCamera(CameraUpdateFactory.newLatLng(it.position))
                    onShopSelectedListener?.onShopSelected(shop)
                }
            }
            false
        }
        map?.setOnMapLoadedCallback(this)
        map?.setOnMapClickListener { onOutsideClickListener?.onClickOutside() }
        positionStorage.restorePosition()
                ?.let { map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), it.zoom)) }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            map?.isMyLocationEnabled = true
    }

    fun getPosition(): Position? {
        val cameraPosition = map?.cameraPosition
        val target = cameraPosition?.target
        if (cameraPosition != null && target != null)
            return Position(target.latitude, target.longitude, cameraPosition.zoom)
        return null
    }

    override fun setData(data: List<IKebabShop>?) {
        if (map != null && data != null) {
            bounds = LatLngBounds.Builder()
            for (shop in data) {
                if (shop.getLatitude() != null && shop.getLongitude() != null) {
                    val latitude = shop.getLatitude()!!
                    val longitude = shop.getLongitude()!!
                    val latLng = LatLng(latitude, longitude)
                    val markerOptions = MarkerOptions()
                            .position(latLng)
                            .title(shop.getName())
                            .snippet(shop.getAddress())
                    val marker = map?.addMarker(markerOptions)
                    markers.put(marker, shop)
                    bounds?.include(latLng)
                }
            }
        }
    }

    override fun onDestroy() {
        getPosition()?.let { positionStorage.savePosition(it) }
        super.onDestroy()
    }

    companion object {
        val TAG = "SupportMapFragment"
    }
}