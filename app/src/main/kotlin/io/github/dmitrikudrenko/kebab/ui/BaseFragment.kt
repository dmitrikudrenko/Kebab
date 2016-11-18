package io.github.dmitrikudrenko.kebab.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import io.github.dmitrikudrenko.kebab.mvp.Contract


abstract class BaseFragment<in View, Presenter> : Fragment() where View : Contract.IView, Presenter : Contract.IPresenter<View> {
    abstract var presenter: Presenter

    override fun onViewCreated(view: android.view.View?, savedInstanceState: Bundle?) {
        presenter.subscribe(this as View)
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }
}