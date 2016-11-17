package io.github.dmitrikudrenko.kebab.mvp


interface IPresenter<in View : IView> {
    fun onCreate(view: View)

    fun onStart() {
    }

    fun onStop() {
    }
}