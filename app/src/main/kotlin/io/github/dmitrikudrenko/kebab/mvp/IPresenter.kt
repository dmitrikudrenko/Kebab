package io.github.dmitrikudrenko.kebab.mvp


interface IPresenter<in View : IView> {
    fun init(view: View)
    fun onStart() {
    }

    fun onStop() {
    }
}