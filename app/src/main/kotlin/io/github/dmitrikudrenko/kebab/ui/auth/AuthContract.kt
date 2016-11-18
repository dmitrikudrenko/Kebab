package io.github.dmitrikudrenko.kebab.ui.auth

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.mvp.IPresenter
import io.github.dmitrikudrenko.kebab.mvp.IView


interface AuthContract {
    interface AuthView: IView {
        fun setupUI(firebaseUser: FirebaseUser?)
        fun showError(message: String?)
        fun onEmptyLogin()
        fun onEmptyPassword()
        fun startActivityForResult(intent: Intent, requestCode: Int)
    }

    interface AuthPresenter : IPresenter<AuthView> {
        fun onSignInClick()
        fun onSignOutClick()
        fun onSignInViaMail(login: CharSequence, password: CharSequence)
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    }
}