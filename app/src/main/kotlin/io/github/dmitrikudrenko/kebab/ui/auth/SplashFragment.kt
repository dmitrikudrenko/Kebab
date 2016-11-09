package io.github.dmitrikudrenko.kebab.ui.auth

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.BR
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.databinding.FSplashBinding
import io.github.dmitrikudrenko.kebab.ui.ProgressDialogFragment
import io.github.dmitrikudrenko.kebab.ui.auth.presenter.AuthPresenter
import io.github.dmitrikudrenko.kebab.ui.auth.view.AuthView
import javax.inject.Inject


class SplashFragment : Fragment(), AuthView {
    @Inject
    lateinit var presenter: AuthPresenter

    private var binding: FSplashBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        KebabApplication.graph.inject(this)
        binding = DataBindingUtil.inflate<FSplashBinding>(inflater, R.layout.f_splash, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.init(this)
        binding?.setVariable(BR.presenter, presenter)
    }

    override fun setSignInEnabled(enabled: Boolean) {
        binding?.signInWithGoogle?.isEnabled = enabled
    }

    override fun setSignOutEnabled(enabled: Boolean) {
        binding?.signOutWithGoogle?.isEnabled = enabled
    }

    override fun setupUI(firebaseUser: FirebaseUser?) {
        binding?.signInWithGoogle?.isEnabled = firebaseUser == null
        binding?.signOutWithGoogle?.isEnabled = firebaseUser != null
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

    override fun showProgressDialog(): Boolean {
        ProgressDialogFragment.create().show(childFragmentManager, ProgressDialogFragment.TAG)
        return true
    }

    override fun hideProgressDialog(): Boolean {
        (childFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) as ProgressDialogFragment?)?.dismissAllowingStateLoss()
        return true
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
