package io.github.dmitrikudrenko.kebab.ui.auth.presenter

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
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
        view.googleAccountAuth(Auth.GoogleSignInApi.getSignInIntent(googleApiClient))
    }

    override fun onSignOutClick() {
        firebaseAuth.signOut()
        Auth.GoogleSignInApi.revokeAccess(googleApiClient)
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

    override fun onGoogleSignedIn(data: Intent?) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        if (result.isSuccess) {
            authViaGoogleAccount(result.signInAccount)
        }
    }

    private fun authViaGoogleAccount(googleSignInAccount: GoogleSignInAccount?) {
        view.showProgressDialog()
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null))
                .addOnCompleteListener(view.getActivity()) {
                    if (!it.isSuccessful)
                        view.showError("Authentication failed.")
                    view.hideProgressDialog()
                }
    }
}