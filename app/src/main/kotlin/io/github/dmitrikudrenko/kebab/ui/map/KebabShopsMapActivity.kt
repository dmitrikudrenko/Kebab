package io.github.dmitrikudrenko.kebab.ui.map

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.internal.PositionStorage
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop
import io.github.dmitrikudrenko.kebab.databinding.AKebabMapBinding
import io.github.dmitrikudrenko.kebab.ui.shop.KebabShopActivity
import javax.inject.Inject

class KebabShopsMapActivity : AppCompatActivity(), OnShopSelectedListener, OnOutsideClickListener {
    @Inject
    lateinit var positionStorage: PositionStorage

    private var fragment: KebabShopsMapFragment? = null
    private var binding: AKebabMapBinding? = null
    private var bottomSheetBehaviour: BottomSheetBehavior<View>? = null
    private var bottomViewTextName: TextView? = null
    private var bottomViewTextAddress: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<AKebabMapBinding>(this, R.layout.a_kebab_map)
        KebabApplication.graph.inject(this)
        injectViews(savedInstanceState)
    }

    private fun injectViews(savedInstanceState: Bundle?) {
        setSupportActionBar(binding?.toolbar)
        if (savedInstanceState == null) {
            fragment = KebabShopsMapFragment()
            fragment?.setPosition(positionStorage.restorePosition())
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, TAG)
                    .commitNow()
        } else fragment = supportFragmentManager.findFragmentByTag(TAG) as KebabShopsMapFragment?

        bottomSheetBehaviour = BottomSheetBehavior.from(binding?.bottomSheet)
        bottomViewTextName = binding?.bottomSheet?.findViewById(R.id.name) as TextView?
        bottomViewTextAddress = binding?.bottomSheet?.findViewById(R.id.address) as TextView?
        hideBottomSheet()
    }

    override fun onShopSelected(shop: IKebabShop): Boolean {
        bottomSheetBehaviour?.peekHeight = resources.getDimension(R.dimen.bottom_sheet_peek_height).toInt()
        bottomSheetBehaviour?.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomViewTextName?.text = shop.getName()
        bottomViewTextAddress?.text = shop.getAddress()
        startActivity(Intent(this, KebabShopActivity::class.java).putExtra("id", shop.getId()))
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