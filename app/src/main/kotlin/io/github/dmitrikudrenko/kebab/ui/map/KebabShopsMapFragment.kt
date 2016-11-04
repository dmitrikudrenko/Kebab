package io.github.dmitrikudrenko.kebab.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.ArrayMap
import android.util.Log
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
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.data.storage.IKebabShopDataController
import javax.inject.Inject

class KebabShopsMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    @Inject
    lateinit var kebabShopDataController: IKebabShopDataController

    private var position: Position? = null
    private val markers = ArrayMap<Marker, IKebabShop>()
    private var bounds: LatLngBounds.Builder? = null

    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
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
        googleMap = map
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            map?.isMyLocationEnabled = true
        position?.let { map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), it.zoom)) }
        loadData(map)
    }

    private fun loadData(map: GoogleMap?) {
        kebabShopDataController.getKebabShops()
                .subscribe(
                        {
                            presentShops(it, map)
                            map?.setOnMapLoadedCallback {
                                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds?.build(), 200))
                            }
                            map?.setOnMarkerClickListener(this)
                            map?.setOnMapClickListener(this)
                        },
                        { Log.e("Data loading", it.message, it) }
                )
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            val shop = markers[marker]
            if (shop != null) {
                googleMap?.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
                return onShopSelectedListener?.onShopSelected(shop) ?: false
            }
        }
        return false
    }

    override fun onMapClick(p0: LatLng?) {
        onOutsideClickListener?.onClickOutside()
    }

    private fun presentShops(list: List<IKebabShop>?, map: GoogleMap?) {
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
                    val marker = map.addMarker(markerOptions)
                    markers.put(marker, shop)
                    bounds?.include(latLng)
                }
            }
        }
    }

    fun getPosition(): Position? {
        val cameraPosition = googleMap?.cameraPosition
        val target = cameraPosition?.target
        if (cameraPosition != null && target != null)
            return Position(target.latitude, target.longitude, cameraPosition.zoom)
        return null
    }

    fun setPosition(position: Position?) {
        this.position = position
    }

    companion object {
        val TAG = "SupportMapFragment"
    }
}