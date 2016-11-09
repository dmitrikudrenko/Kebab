package io.github.dmitrikudrenko.kebab.ui.auth.view

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.mvp.IView


interface AuthView: IView {
    fun setSignInEnabled(enabled: Boolean)
    fun setSignOutEnabled(enabled: Boolean)
    fun setupUI(firebaseUser: FirebaseUser?)
    fun showError(message: String?)
    fun googleAccountAuth(intent: Intent)
}