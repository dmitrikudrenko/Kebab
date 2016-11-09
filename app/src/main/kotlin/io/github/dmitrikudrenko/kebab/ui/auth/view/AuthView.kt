package io.github.dmitrikudrenko.kebab.ui.auth.view

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.mvp.IView


interface AuthView: IView {
    fun setupUI(firebaseUser: FirebaseUser?)
    fun showError(message: String?)
    fun startActivityForResult(intent: Intent, requestCode: Int)
}