package io.github.dmitrikudrenko.kebab.mvp


interface IPresenter<in View : IView> {
    fun subscribe(view: View)
    fun unsubscribe()

    fun onStart() {}
    fun onStop() {}
    fun onPause() {}
    fun onResume() {}
}