package io.github.dmitrikudrenko.kebab.ui.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.dmitrikudrenko.kebab.R


class SplashFragment : Fragment() {
    private var signInWithGoogleBtn: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.f_splash, container, false)
        signInWithGoogleBtn = view?.findViewById(R.id.sign_in_with_google) as Button?
        return view
    }
}