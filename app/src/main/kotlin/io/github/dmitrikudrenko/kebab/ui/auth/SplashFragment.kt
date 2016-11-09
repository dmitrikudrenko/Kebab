package io.github.dmitrikudrenko.kebab.ui.auth

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.ui.ProgressDialogFragment
import io.github.dmitrikudrenko.kebab.ui.auth.presenter.AuthPresenter
import io.github.dmitrikudrenko.kebab.ui.auth.view.AuthView
import javax.inject.Inject


class SplashFragment : Fragment(), AuthView {
    private var signInWithGoogleBtn: Button? = null
    private var signOutWithGoogleBtn: Button? = null

    @Inject
    lateinit var presenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        KebabApplication.graph.inject(this)
        val view = inflater?.inflate(R.layout.f_splash, container, false)
        signInWithGoogleBtn = view?.findViewById(R.id.sign_in_with_google) as Button?
        signOutWithGoogleBtn = view?.findViewById(R.id.sign_out_with_google) as Button?

        signInWithGoogleBtn?.setOnClickListener {
            presenter.onSignInClick()
        }
        signOutWithGoogleBtn?.setOnClickListener {
            presenter.onSignOutClick()
        }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.init(this)
    }

    override fun setSignInEnabled(enabled: Boolean) {
        signInWithGoogleBtn?.isEnabled = enabled
    }

    override fun setSignOutEnabled(enabled: Boolean) {
        signOutWithGoogleBtn?.isEnabled = enabled
    }

    override fun setupUI(firebaseUser: FirebaseUser?) {
        signOutWithGoogleBtn?.isEnabled = firebaseUser != null
        signInWithGoogleBtn?.isEnabled = firebaseUser == null
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            presenter.onGoogleSignedIn(data)
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showProgressDialog() {
        ProgressDialogFragment.create().show(childFragmentManager, ProgressDialogFragment.TAG)
    }

    override fun hideProgressDialog() {
        (childFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) as ProgressDialogFragment?)?.dismissAllowingStateLoss()
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun googleAccountAuth(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
    }

    companion object {
        val RC_SIGN_IN = 9001
    }
}
