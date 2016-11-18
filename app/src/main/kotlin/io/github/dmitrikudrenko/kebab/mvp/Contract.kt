package io.github.dmitrikudrenko.kebab.mvp

import android.support.v4.app.FragmentActivity


interface Contract {
    interface IView {
        fun showProgressDialog() {
        }

        fun hideProgressDialog() {
        }

        fun getActivity(): FragmentActivity
    }

    interface IPresenter<in View : IView> {
        fun subscribe(view: View)
        fun unsubscribe()

        fun onStart() {
        }

        fun onStop() {
        }

        fun onPause() {
        }

        fun onResume() {
        }
    }
}