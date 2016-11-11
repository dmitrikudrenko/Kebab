package io.github.dmitrikudrenko.kebab.ui.auth.presenter

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import io.github.dmitrikudrenko.kebab.ui.auth.view.AuthView


class AuthPresenterImpl() : AuthPresenter, GoogleApiClient.OnConnectionFailedListener {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var googleApiClient: GoogleApiClient? = null
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    private lateinit var view: AuthView

    override fun init(view: AuthView) {
        this.view = view
        authListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                Log.d("User id", firebaseUser.uid)
            } else Log.d("User", "unauthorized")

            view.setupUI(firebaseUser)
        }
        setupGoogleApi()
        view.setupUI(firebaseAuth.currentUser)
    }

    private fun setupGoogleApi() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("939643994696-abj187kgngjvb5ul4ei7sluflitdcfrv.apps.googleusercontent.com")
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(view.getActivity())
                .enableAutoManage(view.getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build()
    }

    override fun onSignInClick() {
        view.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), RC_SIGN_IN)
    }

    override fun onSignOutClick() {
        firebaseAuth.signOut()
        Auth.GoogleSignInApi.revokeAccess(googleApiClient)
    }

    override fun onSignInViaMail(login: CharSequence, password: CharSequence) {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            view.showProgressDialog()
            firebaseAuth.signInWithEmailAndPassword(login.toString(), password.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful)
                            handleSignInError(it.exception, login, password)
                        view.hideProgressDialog()
                    }
        } else view.showError("Login and password can't be empty")
    }

    private fun handleSignInError(exception: Exception?, login: CharSequence, password: CharSequence) {
        if (exception != null) {
            when (exception.javaClass) {
                FirebaseAuthInvalidUserException::class.java -> createUser(login, password)
                else -> view.showError(exception.message ?: "Authentication exception.")
            }
        } else view.showError("Authentication exception.")
    }

    private fun createUser(login: CharSequence, password: CharSequence) {
        view.showProgressDialog()
        firebaseAuth.createUserWithEmailAndPassword(login.toString(), password.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful)
                        view.showError(it.exception?.message ?: "Authentication exception.")
                }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        view.showError(connectionResult.errorMessage)
    }

    override fun onStart() {
        firebaseAuth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        firebaseAuth.removeAuthStateListener(authListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                authViaGoogleAccount(result.signInAccount)
            }
            return true
        }
        return false
    }

    private fun authViaGoogleAccount(googleSignInAccount: GoogleSignInAccount?) {
        view.showProgressDialog()
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null))
                .addOnCompleteListener(view.getActivity()) {
                    if (!it.isSuccessful)
                        view.showError(it.exception?.message ?: "Authentication exception.")
                    view.hideProgressDialog()
                }
    }

    companion object {
        val RC_SIGN_IN = 9001
    }
}