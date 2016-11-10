package io.github.dmitrikudrenko.kebab.mvp

import android.support.v4.app.FragmentActivity


interface IView {
    fun showProgressDialog() {
    }

    fun hideProgressDialog() {
    }

    fun getActivity(): FragmentActivity
}