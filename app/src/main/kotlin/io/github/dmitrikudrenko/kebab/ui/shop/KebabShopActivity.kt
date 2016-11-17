package io.github.dmitrikudrenko.kebab.ui.shop

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.data.model.IKebabShop


class KebabShopActivity : AppCompatActivity(), OnShopLoadingListener {
    private var fragment: KebabShopFragment? = null
    private var toolbar: Toolbar? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var collapsingImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_kebab_shop)
        if (savedInstanceState == null) {
            fragment = KebabShopFragment()
            fragment?.arguments = intent.extras
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, TAG)
                    .commitNow()
        } else fragment = supportFragmentManager.findFragmentByTag(TAG) as KebabShopFragment?
        injectViews()
    }

    private fun injectViews() {
        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout) as CollapsingToolbarLayout?
        collapsingImageView = findViewById(R.id.collapsing_image_view) as ImageView?

        collapsingToolbarLayout?.setExpandedTitleColor(Color.TRANSPARENT)
    }

    override fun onShopLoaded(shop: IKebabShop) {
        Picasso.with(this).load(shop.getImage()).fit().centerCrop().into(collapsingImageView)
    }

    companion object {
        val TAG = "KebabShopFragment"
        val KEY_ID = "id"

        fun startActivity(context: Context, kebabShop: IKebabShop) {
            context.startActivity(Intent(context, KebabShopActivity::class.java).putExtra(KEY_ID, kebabShop.getId()))
        }
    }
}