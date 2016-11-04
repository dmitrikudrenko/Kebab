package io.github.dmitrikudrenko.kebab.ui.map

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.internal.PositionStorage
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import javax.inject.Inject

class KebabShopsMapActivity : AppCompatActivity(), OnShopSelectedListener, OnOutsideClickListener {
    @Inject
    lateinit var positionStorage: PositionStorage

    private var fragment: KebabShopsMapFragment? = null
    private var toolbar: Toolbar? = null
    private var content: ViewGroup? = null
    private var bottomSheetView: View? = null
    private var bottomSheetBehaviour: BottomSheetBehavior<View>? = null
    private var bottomViewTextName: TextView? = null
    private var bottomViewTextAddress: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_kebab_map)
        KebabApplication.graph.inject(this)
        injectViews(savedInstanceState)
    }

    private fun injectViews(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        content = findViewById(R.id.content) as ViewGroup?
        if (savedInstanceState == null) {
            fragment = KebabShopsMapFragment()
            fragment?.setPosition(positionStorage?.restorePosition())
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, TAG)
                    .commitNow()
        } else fragment = supportFragmentManager.findFragmentByTag(TAG) as KebabShopsMapFragment?

        bottomSheetView = findViewById(R.id.bottom_sheet)
        bottomViewTextName = bottomSheetView?.findViewById(R.id.name) as TextView?
        bottomViewTextAddress = bottomSheetView?.findViewById(R.id.address) as TextView?
        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetView)
        hideBottomSheet()
    }

    override fun onShopSelected(shop: IKebabShop): Boolean {
        bottomSheetBehaviour?.peekHeight = resources.getDimension(R.dimen.bottom_sheet_peek_height).toInt()
        bottomSheetBehaviour?.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomViewTextName?.text = shop.getName()
        bottomViewTextAddress?.text = shop.getAddress()
        return true
    }

    override fun onClickOutside() {
        hideBottomSheet()
    }

    private fun hideBottomSheet() {
        bottomSheetBehaviour?.peekHeight = 0
        bottomSheetBehaviour?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onBackPressed() {
        fragment?.getPosition()?.let {
            positionStorage.savePosition(it)
        }
        super.onBackPressed()
    }

    companion object {
        val TAG = "KebabShopsMapFragment"
    }
}