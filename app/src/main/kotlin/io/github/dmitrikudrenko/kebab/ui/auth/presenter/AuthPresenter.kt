package io.github.dmitrikudrenko.kebab.ui.auth.presenter

import android.content.Intent
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.ui.auth.view.AuthView


interface AuthPresenter : IPresenter<AuthView> {
    fun onSignInClick()
    fun onSignOutClick()
    fun onGoogleSignedIn(data: Intent?)
}