package io.github.dmitrikudrenko.kebab.mvp

import android.support.v4.app.FragmentActivity


interface IView {
    fun showProgressDialog() = true
    fun hideProgressDialog() = true
    fun getActivity(): FragmentActivity
}