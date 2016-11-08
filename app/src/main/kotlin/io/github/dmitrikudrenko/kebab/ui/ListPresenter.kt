package io.github.dmitrikudrenko.kebab.ui

import java.util.*

abstract class ListPresenter<T> {
    private val data: MutableList<T> = ArrayList<T>()

    fun getItem(position: Int) = data[position]
    fun getCount() = data.size

    open fun hasStableIds() = false
    open fun getItemId(position: Int) = -1L

    fun onDataChanged(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
    }
}