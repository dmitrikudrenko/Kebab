package io.github.dmitrikudrenko.kebab.ui.auth

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import io.github.dmitrikudrenko.kebab.BR
import io.github.dmitrikudrenko.kebab.KebabApplication
import io.github.dmitrikudrenko.kebab.R
import io.github.dmitrikudrenko.kebab.databinding.FSplashBinding
import io.github.dmitrikudrenko.kebab.ui.BaseFragment
import io.github.dmitrikudrenko.kebab.ui.ProgressDialogFragment
import javax.inject.Inject


class SplashFragment : BaseFragment<AuthContract.AuthView, AuthContract.AuthPresenter>(), AuthContract.AuthView {
    @Inject
    override lateinit var presenter: AuthContract.AuthPresenter

    private var binding: FSplashBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        KebabApplication.graph.inject(this)
        binding = DataBindingUtil.inflate<FSplashBinding>(inflater, R.layout.f_splash, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.subscribe(this)
        binding?.setVariable(BR.presenter, presenter)
    }

    override fun setupUI(firebaseUser: FirebaseUser?) {
        binding?.signInWithGoogle?.isEnabled = firebaseUser == null
        binding?.signOut?.isEnabled = firebaseUser != null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!presenter.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showProgressDialog() {
        if (childFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) == null)
            ProgressDialogFragment.create().show(childFragmentManager, ProgressDialogFragment.TAG)
    }

    override fun hideProgressDialog() {
        (childFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) as ProgressDialogFragment?)?.dismissAllowingStateLoss()
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onEmptyLogin() {
        binding?.emailLoginContainer?.loginTextLayout?.error = getString(R.string.auth_error_empty_login)
    }

    override fun onEmptyPassword() {
        binding?.emailLoginContainer?.passwordTextLayout?.error = getString(R.string.auth_error_empty_password)
    }
}
