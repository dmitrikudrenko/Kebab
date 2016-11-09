package io.github.dmitrikudrenko.kebab.ui.map

import android.util.ArrayMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.dmitrikudrenko.kebab.data.internal.Position
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop


class MapPresenter {
    private var map: GoogleMap? = null
    private val markers = ArrayMap<Marker, IKebabShop>()
    private var onShopSelectedListener: OnShopSelectedListener? = null
    private var onOutsideClickListener: OnOutsideClickListener? = null
    private var bounds: LatLngBounds.Builder? = null

    fun onMapReady(googleMap: GoogleMap?, position: Position?) {
        map = googleMap
        map?.setOnMarkerClickListener {
            if (it != null) {
                val shop = markers[it]
                if (shop != null) {
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLng(it.position))
                    onShopSelectedListener?.onShopSelected(shop)
                }
            }
            false
        }
        map?.setOnMapLoadedCallback {
            map!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds?.build(), 200))
        }
        map?.setOnMapClickListener { onOutsideClickListener?.onClickOutside() }
        position?.let { map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), it.zoom)) }
    }

    fun setOnShopSelectedListener(onShopSelectedListener: OnShopSelectedListener?) {
        this.onShopSelectedListener = onShopSelectedListener
    }

    fun setOnOutsideClickListener(onOutsideClickListener: OnOutsideClickListener?) {
        this.onOutsideClickListener = onOutsideClickListener
    }

    fun presentShops(list: List<IKebabShop>?) {
        if (map != null && list != null) {
            bounds = LatLngBounds.Builder()
            for (shop in list) {
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

    fun getPosition(): Position? {
        val cameraPosition = map?.cameraPosition
        val target = cameraPosition?.target
        if (cameraPosition != null && target != null)
            return Position(target.latitude, target.longitude, cameraPosition.zoom)
        return null
    }

    fun onDestroy() {
        this.map = null
    }
}