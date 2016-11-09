package io.github.dmitrikudrenko.kebab.ui.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.dmitrikudrenko.kebab.R


class SplashActivity : AppCompatActivity() {
    private lateinit var fragment: SplashFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_splash)
        if (savedInstanceState == null) {
            fragment = SplashFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, TAG)
                    .commitNow()
        } else fragment = supportFragmentManager.findFragmentByTag(TAG) as SplashFragment
    }

    companion object {
        val TAG = "SplashFragment"
    }
}