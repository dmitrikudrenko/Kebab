package io.github.dmitrikudrenko.kebab.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.dmitrikudrenko.kebab.R

class KebabShopsListActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fragment: KebabShopsListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_kebab_list)
        injectViews(savedInstanceState)
    }

    private fun injectViews(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            fragment = KebabShopsListFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, TAG)
                    .commitNow()
        } else fragment = supportFragmentManager.findFragmentByTag(TAG) as KebabShopsListFragment?
    }

    companion object {
        val TAG = "KebabShopsListFragment"
    }
}