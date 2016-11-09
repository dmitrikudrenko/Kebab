package io.github.dmitrikudrenko.kebab.analytics


interface AnalyticsReporter {
    fun setUser(id: String)
    fun setUserProperty(key: String, value: String?)
    fun log(message: String, params: Map<String, String>? = null)
}